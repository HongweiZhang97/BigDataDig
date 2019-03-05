package tool.samplesprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * 封装的样本类，从文件中读取数据填入数组，生成神经网络训练或测试样本。
 * <p>
 * 文件格式要求：n位表示输入属性，1位标签表示输出，输出可前置或后置。
 * 输出值需数值化为1,2,3...
 * <p>
 * 备注：
 * <br>
 * 对较大的训练样本没有优化，可能会无法处理，但速度快。
 * <br>
 * 输出需进行处理才能的到神经网络的输出结果。
 * 例如：输出为n，则对于使用sigmoid激活函数的神经网络，可以对n进行二进制编码，得到理想输出
 * @author Zhang Hongwei
 */
public class SimpleSamples implements Samples{
	private String samPath = null; // 样本源文件
	private int start; // 样本起始位置
	private int numOfSam; // 样本个数
	private int dimension; // 输入维数
	boolean outInFront = false; // 样本格式判断，是否输出在前
	private double[][] sample = null; // 保存样本数据
	private double[] samInput = null; // 单次样本输入
	private double samOutput; // 单次样本输出
	
	/**
	 * 初始化本属性，从文件中读取数据存入sample[][]数组中。
	 * @param fileName 文件名
	 * @param start 从文件第几行开始获取样本数据
	 * @param numOfSam 共读取的样本数据个数
	 * @param dimension 样本输入维数
	 * @param outInFront 是否输出在前
	 */
	public SimpleSamples(String filePath, int start, int numOfSam, int dimension, boolean outInFront) {
		this.samPath = filePath;
		this.start = start;
		this.numOfSam = numOfSam;
		this.dimension = dimension;
		this.outInFront = outInFront;
		sample = new double[numOfSam][dimension+1];
		samInput = new double[dimension];
		initSam();
	}
	
	private void initSam() {
		Scanner scanSam = null;
		try {
				File sam = new File(samPath);
				if(!sam.exists()) {
					System.out.println("样本文件不存在");
				}
				scanSam = new Scanner(sam);
				for(int i = 0; i < start-1; i++) {
					scanSam.nextLine();
				}
				if(outInFront) {
					for(int i = 0; i < this.numOfSam; i++) {
						for(int j = 0; j < this.dimension+1; j++) {
							sample[i][j] = scanSam.nextDouble();
						}
					}
				}else {
					for(int i = 0; i < this.numOfSam; i++)	{
						for(int j = 1; j < this.dimension+1; j++) {
							sample[i][j] = scanSam.nextDouble();
						}
						sample[i][0] = scanSam.nextDouble();
					}
				}
			}catch(FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("找不到训练样本文件");
			}finally {
				scanSam.close();
			}
	}
	
	public void fillSample(int samIndex) {
		samOutput = sample[samIndex][0];
		for(int i = 0; i < dimension; i++) {
			samInput[i] = sample[samIndex][i+1];
		}
	}
	
	public void getSamInput(double[] samIn) {
		for(int i = 0; i < dimension; i++) {
			samIn[i] = samInput[i];
		}
	}
	
	public double getSamOutput() {
		return samOutput;
	}
	
	public int getNumOfSam() {
		return numOfSam;
	}
	
	/**
	 * 小数点后保留6位输出样本
	 */
	public void printSam() {
		DecimalFormat dfmt = new DecimalFormat("0.000000");
		for(int i = 0; i < numOfSam; i++) {
			System.out.print("第"+(i + 1)+"行:");
			for(int j = 0; j < dimension+1; j++) {
				if(this.sample[i][j] < 0) {
					System.out.print("  "+dfmt.format(sample[i][j]));
				}else {
					System.out.print("   "+dfmt.format(sample[i][j]));
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}


