package tool.neuralnetwork;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import tool.samplesprocess.Samples;

/**
 * 封装的单隐层神经网络，训练算法为BP算法，需配合Samples类使用。
 * <p>
 * 备注：网络训练方式有增量训练和批量训练两种。
 * <br>
 * 本类中使用地址赋值，如需改变相应代码，需注意java中无指针变量，对地址的改变不可传递，
 * 每次更新地址都要同步更新所有关联量
 * @author Zhang Hongwei
 */
public class BackProNetwork {
	// 网络结构
	private int inputNum;
	private int hidNum;
	private int outputNum;
	// 样本输入输出
	private double[] samIn = null;
	private double[] samOut = null;
	// 隐层输入输出
	private double[] hidLayerIn = null;
	private double[] hidLayerOut = null;
	// 输出层输入输出
	private double[] outLayerIn = null;
	private double[] outLayerOut = null;
	// 输入层到隐层权值阈值
	private double[][] inHidWeight = null;
	private double[] inHidThreshold = null;
	// 输入层到隐层权值阈值调整量
	private double[][] deltaInHidWeight = null;
	private double[] deltaInHidThreshold = null;
	// 隐层到输出层权值阈值
	private double[][] hidOutWeight = null;
	private double[] hidOutThreshold = null;
	// 隐层到输出层权值阈值调整量
	private double[][] deltaHidOutWeight = null;
	private double[] deltaHidOutThreshold = null;
	// 敏感性
	private double[] hidLayerSen = null;
	private double[] outLayerSen = null;
	// 训练精度和测试精度
	private double trainAcc;
	private double testAcc;
	private double trainMistake = 1;
	private double testMistake;
	private double preError;
	private double curError;
	private enum samClass{TRAIN, TEST};
	// 网络训练参数
	private long trainNum = 10000;
	private double learningRate = 0.5;
	private double momentum = 0.5;
	private double desiredTrainAcc = 0.9;
	private double desiredTrainMistake = 0.01;
	private boolean useBatchLearning = false;
	// 输出位数控制
	private DecimalFormat printFormat = new DecimalFormat("0.0000");
	
	/**
	 * 网络初始化，需输入网络结构。
	 * @param inputNum 网络输入维数
	 * @param hidNum 网络隐层神经元数
	 * @param outputNum 网络输出层神经元数
	 */
	public BackProNetwork(int inputNum, int hidNum, int outputNum) {
		this.inputNum = inputNum;
		this.hidNum = hidNum;
		this.outputNum = outputNum;
		samIn = new double[inputNum];
		samOut = new double[outputNum];
		hidLayerIn = samIn;
		hidLayerOut = new double[hidNum];
		outLayerIn = hidLayerOut;
		outLayerOut = new double[outputNum];
		inHidWeight = new double[hidNum][inputNum];
		inHidThreshold = new double[hidNum];
		deltaInHidWeight = new double[hidNum][inputNum];
		deltaInHidThreshold = new double[hidNum];
		hidOutWeight = new double[outputNum][hidNum];
		hidOutThreshold = new double[outputNum];
        deltaHidOutWeight = new double[outputNum][hidNum];
        deltaHidOutThreshold = new double[outputNum];
        hidLayerSen = new double[hidNum];
        outLayerSen = new double[outputNum];
		initWeight();
	}
	
	// 权值初始化
	private void initWeight() {
		initWeight(this.inHidWeight, this.inHidThreshold);
		initWeight(this.hidOutWeight, this.hidOutThreshold);
	}
	
	private void initWeight(double[][] weight, double[] threshold) {
		DecimalFormat dFormat = new DecimalFormat("0.00");
		for(int i = 0; i < weight.length; i++) {
			for(int j = 0; j < weight[0].length; j++) {
				weight[i][j] = Double.valueOf(dFormat.format((new Random().nextDouble()*2-1)));
			}
			threshold[i] = Double.valueOf(dFormat.format((new Random().nextDouble()*2-1)));
		}
	}
	
	/**
	 * 设置网络训练次数，默认为10000次。
	 * @param trainNum 网络训练次数
	 */
	public void setTrainNum(long trainNum) {
		this.trainNum = trainNum;
	}
	
	/**
	 * 设置网络学习率，默认为0.5。
	 * @param learningRate
	 */
	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}
	
	/**
	 * 设置动量项，默认为0.5。
	 * @param momentum
	 */
	public void setMomentum(double momentum) {
		this.momentum = momentum;
	}
	
	/**
	 * 设置目标训练精度，默认为0.9。
	 * @param desiredSamAcc
	 */
	public void setDesiredTrainAcc(double desiredTrainAcc) {
		this.desiredTrainAcc = desiredTrainAcc;
	}
	
	public void setDesiredTrainMistake(double desiredTrainMistake) {
		this.desiredTrainMistake = desiredTrainMistake;
	}

	/**
	 * 反向传播算法训练网络。
	 * @param trainSam 训练样本
	 * @param testSam 测试样本
	 * @param useBatchLearing 是否使用批量学习
	 */
	public void train(Samples trainSam, Samples testSam, boolean useBatchLearning) {
		printData(trainSam);
		setTrainMethod(useBatchLearning);
		int curruntNum = 0;
		while(curruntNum < trainNum && (trainAcc < desiredTrainAcc && trainMistake > desiredTrainMistake)) {
			learn(trainSam);
			getAccuracy(trainSam, samClass.TRAIN);
			curruntNum++;
			System.out.println("第" + curruntNum + "次训练样本收敛率：" + printFormat.format(trainAcc) + " 均方误差：" + printFormat.format(trainMistake));
		}
		getAccuracy(testSam, samClass.TEST);
		printResult(curruntNum);
		saveData();
	}
	
	// 打印样本
	public void printData(Samples sam) {
		sam.printSam();
		printWeight();
	}
	
	// 打印权值
    public void printWeight() {
    	System.out.println();
		System.out.println("网络结构:Input-" + inputNum + "-Hidden-" + hidNum + "-output-" + outputNum);
		printInHidWeight();
		printHidOutWeight();
		System.out.println();
	}
    

	
	private void printInHidWeight() {
		System.out.println("输入层到隐层权值阈值(" + hidNum + "x" + (inputNum + 1) + "):");
		System.out.println("*******************************");
		for(int i = 0; i < hidNum; i++)
		{
			System.out.print((i + 1) + ":");
			for(int j = 0; j < inputNum; j++) {
				double weight = inHidWeight[i][j];
				if (weight < 0) {
					System.out.print("  ");
				} else {
					System.out.print("   ");
				}
				System.out.print(weight);
			}
			double threshold = inHidThreshold[i];
			if (threshold < 0) {
				System.out.print("  ");
			} else {
				System.out.print("   ");
			}
			System.out.println(threshold);
		}
	}
	
	private void printHidOutWeight() {
		System.out.println("隐层到输出层权值阈值(" + outputNum + "x" + (hidNum + 1) + "):");
		System.out.println("*******************************");
		for(int i = 0; i < outputNum; i++)
		{
			System.out.print((i + 1) + ":");
			for(int j = 0; j < hidNum; j++) {
				double weight = hidOutWeight[i][j];
				if(weight < 0) {
					System.out.print("  ");
				}else {
					System.out.print("   ");
				}
				System.out.print(weight);
			}
			double threshold = hidOutThreshold[i];
			if(threshold < 0) {
				System.out.print("  ");
			}else {
				System.out.print("   ");
			}
			System.out.println(threshold);
		}
	}
	
    public void saveData() {
    	try {
    		//输出权值
			FileOutputStream weightsOut=new FileOutputStream("E:\\Eclipse for JavaEE workplace\\BigDataDig\\data\\weight.txt",false);
			for(int i = 0; i < hidNum; i++) {
				for(int j = 0; j < inputNum; j++) {
					String weight = String.valueOf(inHidWeight[i][j])+"  ";
					weightsOut.write(weight.getBytes());
				}
				String threshold = String.valueOf(inHidThreshold[i])+"\n";
				weightsOut.write(threshold.getBytes());
			}
			for(int i = 0; i < outputNum; i++) {
				for(int j = 0; j < hidNum; j++) {
					String weight = String.valueOf(hidOutWeight[i][j])+"  ";
					weightsOut.write(weight.getBytes());
				}
				String threshold = String.valueOf(hidOutThreshold[i])+"\n";
				weightsOut.write(threshold.getBytes());
			}
			weightsOut.close();
			
			// 输出需要保存的网络参数
			// 训练参数
			String hidNumStr = String.valueOf(hidNum);
			String trainNumStr = String.valueOf(trainNum);
			String learningRateStr = String.valueOf(learningRate);
			String momentumStr = String.valueOf(momentum);
			String desiredTrainAccStr = String.valueOf(desiredTrainAcc);
			String desiredTrainMistakeStr = String.valueOf(desiredTrainMistake);
			String useBatchLearningStr = String.valueOf(useBatchLearning);
			// 训练效果参数
			String trainAccStr = printFormat.format(trainAcc);
			String trainMistakeStr = printFormat.format(trainMistake);
			String testAccStr = printFormat.format(testAcc);
			String testMistakeStr = printFormat.format(testMistake);
			String[] netParams = {hidNumStr, trainNumStr, learningRateStr, momentumStr, 
					desiredTrainAccStr, desiredTrainMistakeStr, useBatchLearningStr, 
					trainAccStr, trainMistakeStr, testAccStr, testMistakeStr};
			FileOutputStream netParamsOut = new FileOutputStream("E:\\Eclipse for JavaEE workplace\\BigDataDig\\data\\netParams.txt",false);
			for(int i = 0; i < netParams.length; i++) {
				String outStr = netParams[i] + "  ";
				netParamsOut.write(outStr.getBytes());
			}
			netParamsOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	private void setTrainMethod(boolean useBatchLearing) {
		if (useBatchLearing) {
			this.useBatchLearning = true;
		}  else {
			this.useBatchLearning = false;
		}
	}
	
	// 单次学习
    private void learn(Samples trainSam) {
    	int[] order = new int[trainSam.getNumOfSam()];
    	shuffle(order);
    	for(int i = 0; i < trainSam.getNumOfSam(); i++) {
    		// java中无指针变量,地址赋值不可传递赋值,每次改变赋值都要进行传递
    		trainSam.fillSample(order[i]);
    		trainSam.getSamInput(samIn);
    	    getSamOut(trainSam.getSamOutput());
    		forward();
    		// adaptSteepness();
    		// calculateError();
    		backPropagate();
    	}
    	if(useBatchLearning) {
    		updateWeight();
    	}
	}
    
    // 创建随机索引数组，随机安排训练顺序
    private void shuffle(int[] order) {
    	List<Integer> list=new ArrayList<>();
    	for(int i = 0; i < order.length; i++) {
    		list.add(i);
    	}
    	Collections.shuffle(list);
    	Iterator<Integer> iterator = list.iterator();
    	for(int i = 0; iterator.hasNext(); i++) {
    		order[i] = iterator.next();
    	}
    }	
    
    // 对样本输出二进制编码得到神经网络正确输出
    private void getSamOut(double output) {
    	int[] binary = new int[outputNum];
    	int decimal = (int) output;
        for(int i = 0; decimal != 0; i++) {
        	binary[i] = decimal % 2;
        	decimal = decimal / 2;
        }
    	for(int i = 0; i < this.samOut.length; i++) {
    		samOut[i] = binary[i];
    	}
    }
    
    // 正向传播
    private void forward() {
    	hidForward();
    	outForward();
    }
    
    private void hidForward() {
    	for(int i = 0; i < this.hidNum; i++) {
    		double s = 0;
    		for(int j = 0; j < this.inputNum; j++) {
    			s += this.hidLayerIn[j] * this.inHidWeight[i][j];
    		}
    		s += this.inHidThreshold[i];
    		this.hidLayerOut[i] = sigmoid(s);
    	}
    }

    private void outForward() {
    	for(int i = 0; i < this.outputNum; i++) {
    		double s = 0;
    		for(int j = 0; j < this.hidNum; j++) {
    			s += this.outLayerIn[j] * this.hidOutWeight[i][j];
    		}
    		s += this.hidOutThreshold[i];
    		this.outLayerOut[i] = sigmoid(s);
    	}
    }
    
    private double sigmoid(double s) {
    	return 1.0 / (1.0 + Math.exp(-s));
    }

    // 反向传播
    private void backPropagate() {
    	outLayerBackPropagate();
    	hidLayerBackPropagate();
    	if (!useBatchLearning) {
    		updateWeight();
    	}
    }
    
    private void outLayerBackPropagate() {
		for(int i = 0; i < outputNum; i++) {
			outLayerSen[i] = (outLayerOut[i] - samOut[i]) * outLayerOut[i] * (1-outLayerOut[i]);
			for(int j = 0; j < hidNum; j++) {
				if (useBatchLearning) {
					deltaHidOutWeight[i][j] -= learningRate * outLayerSen[i] * hidLayerOut[j];
					deltaHidOutThreshold[i] -= learningRate * outLayerSen[i];
				} else {
					deltaHidOutWeight[i][j] = -learningRate * outLayerSen[i] * hidLayerOut[j] + momentum * deltaHidOutWeight[i][j]; 
					deltaHidOutThreshold[i] = -learningRate * outLayerSen[i] + momentum * deltaHidOutThreshold[i];
				}
			}
		}
	}
    
    private void hidLayerBackPropagate() {
		for(int i = 0; i < hidNum; i++) {
			double sen = 0;
			for(int j = 0; j < outputNum; j++) {
				sen += outLayerSen[j] * hidOutWeight[j][i];
			}
			hidLayerSen[i] = sen * hidLayerOut[i] * (1-hidLayerOut[i]);
			for(int j = 0; j < inputNum; j++) {
				if (useBatchLearning) {
					deltaInHidWeight[i][j] -= learningRate * hidLayerSen[i] * hidLayerIn[j];
					deltaInHidThreshold[i] -= learningRate * hidLayerSen[i];
				} else {
					deltaInHidWeight[i][j] = -learningRate * hidLayerSen[i] * hidLayerIn[j] + momentum * deltaInHidWeight[i][j]; 
					deltaInHidThreshold[i] = -learningRate * hidLayerSen[i] + momentum * deltaInHidWeight[i][j];
				}
			}
		}
	}
        
    // 对网络输出进行划分，划分结果用于判断是否与网络正确输出相同
    private double categorizeOutput(double output) {
    	if (output > 0.5) {
    		return 1;
    	} else {
    		return 0;
    	}
//    	if (output > 0.9) {
//    		return 1;
//    	} else if (output < 0.1) {
//    		return 0;
//    	} else {
//    		return -1;
//    	}
    }
    
    // 更新权值
    private void updateWeight() {
    	updateHidOutWeight();
    	updateInHidWeight();
    }
    
    private void updateHidOutWeight() {
    	for(int i = 0; i < outputNum; i++) {
    		for(int j = 0; j < hidNum; j++) {
    			hidOutWeight[i][j] += deltaHidOutWeight[i][j];
    			if (useBatchLearning) {
    				deltaHidOutWeight[i][j] = 0;
    			}
    		}
    		hidOutThreshold[i] += deltaHidOutThreshold[i];
    		if (useBatchLearning) {
    			deltaHidOutThreshold[i] = 0;
    		}
    	}
    }
    
    private void updateInHidWeight() {
    	for(int i = 0; i < hidNum; i++) {
    		for(int j = 0; j < inputNum; j++) {
    			inHidWeight[i][j] += deltaInHidWeight[i][j];
    			if (useBatchLearning) {
    				deltaInHidWeight[i][j] = 0;
    			}
    		}
    		inHidThreshold[i] += deltaInHidThreshold[i];
    		if (useBatchLearning) {
    			deltaInHidThreshold[i] = 0;
    		}
    	}
    }
    
    // 计算本样本均方误差
    private void calculateError() {
    	double error = 0;
    	for(int i = 0; i < outputNum; i++) {
    		error += Math.pow((outLayerOut[i] - samOut[i]), 2);
    	}
    	preError = curError;
    	curError = error;
    }
            
    
    // 计算样本正确率及误差
    private void getAccuracy(Samples sam, samClass samType) {
    	int incorrectNum = 0;
    	double error = 0;
    	for(int i = 0; i < sam.getNumOfSam(); i++) {
    		sam.fillSample(i);
    		sam.getSamInput(samIn);
    	    getSamOut(sam.getSamOutput());
    		forward();
    		boolean incorrect = false;
    		for(int j = 0; j < outputNum; j++) {
    			if (samOut[j] != categorizeOutput(outLayerOut[j])) {
    				incorrect = true;
    			}
    			error += Math.pow((samOut[j] - outLayerOut[j]), 2);
    		}
    		if (incorrect) {
    			incorrectNum++;
    		}
    	}
    	if(samType == samClass.TEST) {
    		testAcc =1.0 - (double)(incorrectNum) / sam.getNumOfSam();
    		testMistake = error / (outputNum * sam.getNumOfSam());
    	} else {
    		trainAcc =1.0 - (double)(incorrectNum) / sam.getNumOfSam();
    		trainMistake = error / (outputNum * sam.getNumOfSam());
    	}
    }
    
    
    // 打印训练结果
    private void printResult(int currentNum) {
    	printWeight();
    	System.out.println();
    	System.out.print("训练结果：");
    	if (trainAcc < desiredTrainAcc ) {
			System.out.println("达到训练次数，训练精度未达标");
		} else {
			System.out.println("训练精度达标");
		}
    	System.out.println("训练次数：" + currentNum);
		System.out.println("训练样本收敛率：" + printFormat.format(trainAcc) + " 均方误差：" + printFormat.format(trainMistake));
		System.out.println("测试样本收敛率：" + printFormat.format(testAcc) + " 均方误差：" + printFormat.format(testMistake));
    }
}



