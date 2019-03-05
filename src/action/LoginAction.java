package action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;

import service.UserService;
import tool.samplesprocess.Params;
//登录界面，控制登录跳转
@SuppressWarnings("serial")
public class LoginAction extends ActionSupport implements SessionAware{
    /**
	 * 
	 */
	private UserService userService;
    private String username;
    private String password;
    //Struts2中Map类型的session
    private Map<String, Object> session;
    //接收客户端传来的验证码
    private String securityCode;
    
    public void setUserService(UserService userService) {
		this.userService = userService;
    }
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
	
	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String execute(){
		if(userService.checkUser(username,password)){
			Params params = new Params();
			ValueStack stack = ActionContext.getContext().getValueStack();
			Map<String, Object> context = new HashMap<String, Object>();
			for(int i = 0; i < params.getParamsNum(); i++) {
				String key = "param" + i;
				context.put(key, params.getParams(i)); 
			}
		    stack.push(context);
	   		return "success";
	   	}else { 
	   		return "error";
	   	}
	}
	
	public void validate() {
		String serverCode = (String)session.get("SESSION_SECURITY_CODE");
        if(!serverCode.equals(securityCode)){
            addFieldError("securityCode", "验证码错误!");
        }
		if (username == null || username.trim().equals(""))
	      {
	         addFieldError("username","必须填写用户名！");
	      }
		if (password == null || password.trim().equals(""))
	      {
	         addFieldError("password","必须填写密码！");
	      }
	}


	
} 
