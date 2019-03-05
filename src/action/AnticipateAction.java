package action;

import com.opensymphony.xwork2.ActionSupport;

import service.AnticipateService;

//Ô¤²âÖÖÀà
public class AnticipateAction extends ActionSupport{
	private AnticipateService antiService;
	private String param1;
	private String param2;
	private String param3;
	private String param4;

	public void setAntiService(AnticipateService antiService) {
		this.antiService = antiService;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	public String getParam4() {
		return param4;
	}
	public void setParam4(String param4) {
		this.param4 = param4;
	}

	
	public String execute() {
		double[] params = new double[4];
		params[0] = Double.valueOf(param1);
		params[1] = Double.valueOf(param2);
		params[2] = Double.valueOf(param3);
		params[3] = Double.valueOf(param4);
		int result = antiService.anticipate(params);
		if(result == 1) {
			return "type1";
		}else if(result == 2) {
			return "type2";
		}else if(result == 3) {
			return "type3";
		}else {
			return "error";
		}
	}


}
