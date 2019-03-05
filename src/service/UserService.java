package service;




import dao.UserDAO;
import entity.User;

public class UserService {
	 private UserDAO userDao;
	 
     public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
     
	public boolean checkUser(String username,String password){
    	 if(userDao.findUser(username, password)){
    		 return true; 
    	 }else return false;
     }
	
	public boolean checkUser(String username){
   	 if(userDao.findUser(username)){
   		 return true; 
   	 }else return false;
    }
	
	public boolean addUser(String username,String password) {
		User user = new User(username, password);
		if(userDao.saveUser(user)) {
			return true;
		}else {
			return false;
		}
	}
}
