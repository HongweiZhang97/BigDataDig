package tool.samplesprocess;

/**
 * 样本接口，规范样本类
 * @author Zhang Hongwei
 */
public interface Samples {
	/**
	 * 根据索引，将指定位置样本填入样本
	 * @param samIndex
	 */
	public void fillSample(int samIndex);
	/**
	 * 获取样本输入，提供给网络使用
	 * @param samIn 网络样本输入数组
	 */
	public void getSamInput(double[] samIn);
	/**
	 * 获取样本输出，提供给网络使用
	 * @return 返回样本输出
	 */
	public double getSamOutput();
	/**
	 * 获取样本个数，提供给网络使用
	 * @return 返回样本总个数
	 */
	public int getNumOfSam();
	/**
	 * 保留小数点后六位，打印样本
	 */
	public void printSam();
}
