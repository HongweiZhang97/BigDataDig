package tool.neuralnetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import tool.samplesprocess.Params;

public class Anticipate {
	// 网络结构
	private int inputNum;
	private int hidNum;
	private int outputNum;
	// 节点参数
	private double[] hidLayerIn = null;
	private double[] hidLayerOut = null;
	private int[] outLayerOut = null;
	private int antiResult = 0;
	// 输入层到隐层权值阈值
	private double[][] inHidWeight = null;
	private double[] inHidThreshold = null;
	// 隐层到输出层权值阈值
	private double[][] hidOutWeight = null;
	private double[] hidOutThreshold = null;

	
	public Anticipate(double[] samIn) {
		inputNum = 4;
		hidNum = Integer.parseInt(new Params().getParams(0));
		outputNum = 2;
		hidLayerIn = new double[inputNum];
		hidLayerOut = new double[hidNum];
		outLayerOut = new int[outputNum];
		inHidWeight = new double[hidNum][inputNum];
		inHidThreshold = new double[hidNum];
		hidOutWeight = new double[outputNum][hidNum];
		hidOutThreshold = new double[outputNum];
		initInput(samIn);
		initWeight();
	}
	
	
	private void initInput(double[] samIn) {
		for(int i = 0; i < this.inputNum; i++) {
			hidLayerIn[i] = samIn[i];
		}
	}
	
	private void initWeight() {
		Scanner scanSam = null;
		try {
				File sam = new File("E:\\Eclipse for JavaEE workplace\\BigDataDig\\data\\weight.txt");
				if(!sam.exists()) {
					System.out.println("权值文件不存在");
				}
				scanSam = new Scanner(sam);
				for(int i = 0; i < hidNum; i++) {
					String[] str = scanSam.nextLine().split("  ");
					for(int j = 0; j < inputNum; j++) {
						inHidWeight[i][j] = Double.valueOf(str[j]);
					}
					inHidThreshold[i] = Double.valueOf(str[str.length-1]);
				}
				for(int i = 0; i < outputNum; i++) {
					String[] str = scanSam.nextLine().split("  ");
					for(int j = 0; j < hidNum; j++) {
						hidOutWeight[i][j] = Double.valueOf(str[j]);
					}
					hidOutThreshold[i] = Double.valueOf(str[str.length-1]);
				}
			}catch(FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("找不到权值文件");
			}finally {
				scanSam.close();
			}
	}
	
	
	public void anticipate() {
		hidForward();
		outForward();
		calAntiResult();
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
    			s += this.hidLayerOut[j] * this.hidOutWeight[i][j];
    		}
    		s += this.hidOutThreshold[i];
    		this.outLayerOut[i] = categorizeOutput(sigmoid(s));
    	}
    }
    
    // 对网络输出进行划分，划分结果用于判断是否与网络正确输出相同
    private int categorizeOutput(double output) {
    	if (output > 0.5) {
    		return 1;
    	} else {
    		return 0;
    	}
    }
    
    private double sigmoid(double s) {
    	return 1.0 / (1.0 + Math.exp(-s));
    }
    
    private void calAntiResult() {
    	for(int i = 0; i < outputNum; i++) {
    		antiResult += outLayerOut[i]*Math.pow(2, i);
    	}
    }
    
    public int getAntiResult() {
    	return antiResult;
    }
    
}
