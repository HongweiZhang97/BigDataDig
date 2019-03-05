package tool.samplesprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Scanner;

import org.apache.struts2.ServletActionContext;

public class Params {
	private String[] paramStr = null;
	private int paramsNum;

	public Params() {
		Scanner paramsScan = null;
		try {
				File params = new File("E:\\Eclipse for JavaEE workplace\\BigDataDig\\data\\netParams.txt");
				paramsScan = new Scanner(params);
				String[] str = paramsScan.nextLine().split("  ");
				paramsNum = str.length;
				paramStr= new String[str.length];
				for(int i = 0; i < str.length; i++) {
					paramStr[i] = str[i];
					System.out.println(paramStr[i]);
				}
			}catch(NullPointerException | FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("找不到参数文件");
			}finally {
				paramsScan.close();
			}
	
	 }
	
	public String getParams(int index) {
		return paramStr[index];
	}
	
	public int getParamsNum() {
		return paramsNum;
	}
}
