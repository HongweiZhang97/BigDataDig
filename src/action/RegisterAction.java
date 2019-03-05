package action;

import org.apache.jasper.tagplugins.jstl.core.If;

import com.opensymphony.xwork2.ActionSupport;

import service.UserService;

//ע�����
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
	         addFieldError("username","������д�û�����");
	    }
		if (password == null || password.trim().equals("")){
	         addFieldError("password","������д���룡");
	    }
		if(passwordConf == null || passwordConf.trim().equals("")) {
			addFieldError("passwordConf","����ȷ�����룡");
		}
		if(!passwordConf.equals(password)) {
			addFieldError("passwordConf", "�������벻һ�£�");
		}
		if(userService.checkUser(username)) {
			addFieldError("username", "�û����Ѵ���");
		}
	}


}
