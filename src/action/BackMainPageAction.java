package action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

import tool.samplesprocess.Params;

//������ҳ������������
public class BackMainPageAction {
	public String execute() {
		Params params = new Params();
		ValueStack stack = ActionContext.getContext().getValueStack();
		Map<String, Object> context = new HashMap<String, Object>();
		for(int i = 0; i < params.getParamsNum(); i++) {
			String key = "param" + i;
			context.put(key, params.getParams(i)); 
		}
	    stack.push(context);
	    return "success";
	}
}
