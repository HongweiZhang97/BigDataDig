package action;

import org.apache.jasper.tagplugins.jstl.core.If;

import com.opensymphony.xwork2.ActionSupport;

import service.UserService;

//注册界面
public class RegisterAction extends ActionSupport{
	private UserService userService;
    private String username;
    private String password;
    private String passwordConf;

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
	
	public String getPasswordConf() {
		return passwordConf;
	}
	
	public void setPasswordConf(String passwordConf) {
		this.passwordConf = passwordConf;
	}
    
	public String execute() {
		if(userService.addUser(username, password)) {
			return "success";
		}else {
			return "error";
		}

	}
	
	public void validate() {
		if (username == null || username.trim().equals("")){
	         addFieldError("username","必须填写用户名！");
	    }
		if (password == null || password.trim().equals("")){
	         addFieldError("password","必须填写密码！");
	    }
		if(passwordConf == null || passwordConf.trim().equals("")) {
			addFieldError("passwordConf","必须确认密码！");
		}
		if(!passwordConf.equals(password)) {
			addFieldError("passwordConf", "两次密码不一致！");
		}
		if(userService.checkUser(username)) {
			addFieldError("username", "用户名已存在");
		}
	}


}
