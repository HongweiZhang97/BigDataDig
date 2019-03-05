package tool.samplesprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * ��װ�������࣬���ļ��ж�ȡ�����������飬����������ѵ�������������
 * <p>
 * �ļ���ʽҪ��nλ��ʾ�������ԣ�1λ��ǩ��ʾ����������ǰ�û���á�
 * ���ֵ����ֵ��Ϊ1,2,3...
 * <p>
 * ��ע��
 * <br>
 * �Խϴ��ѵ������û���Ż������ܻ��޷��������ٶȿ졣
 * <br>
 * �������д�����ܵĵ����������������
 * ���磺���Ϊn�������ʹ��sigmoid������������磬���Զ�n���ж����Ʊ��룬�õ��������
 * @author Zhang Hongwei
 */
public class SimpleSamples implements Samples{
	private String samPath = null; // ����Դ�ļ�
	private int start; // ������ʼλ��
	private int numOfSam; // ��������
	private int dimension; // ����ά��
	boolean outInFront = false; // ������ʽ�жϣ��Ƿ������ǰ
	private double[][] sample = null; // ������������
	private double[] samInput = null; // ������������
	private double samOutput; // �����������
	
	/**
	 * ��ʼ�������ԣ����ļ��ж�ȡ���ݴ���sample[][]�����С�
	 * @param fileName �ļ���
	 * @param start ���ļ��ڼ��п�ʼ��ȡ��������
	 * @param numOfSam ����ȡ���������ݸ���
	 * @param dimension ��������ά��
	 * @param outInFront �Ƿ������ǰ
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
					System.out.println("�����ļ�������");
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
				System.out.println("�Ҳ���ѵ�������ļ�");
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
	 * С�������6λ�������
	 */
	public void printSam() {
		DecimalFormat dfmt = new DecimalFormat("0.000000");
		for(int i = 0; i < numOfSam; i++) {
			System.out.print("��"+(i + 1)+"��:");
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


