package dao;

import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.config.TxNamespaceHandler;

import com.mysql.cj.Session;

import entity.User;

public class UserDAO{
	  private HibernateTemplate hibernateTemplate;
		public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
			this.hibernateTemplate=hibernateTemplate;
		}
	  public boolean saveUser(User m)
	  {
		try {
			this.hibernateTemplate.save(m);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	   return true;
	  }
	//普通用户登录验证
	public boolean findUser(String username,String password){
	  boolean flag = false;
	  System.out.println("************"+username+password);
	  String hql = "from User as u where u.username = '"+username+"' and u.password = '"+password+"'";
	  List<User> userList = (List<User>) this.hibernateTemplate.find(hql);
	  if(userList.size()>0){
	     flag = true;
	  }
	  System.out.println(flag);
	  return flag;
	  }
	
	public boolean findUser(String username){
		  boolean flag = false;
		  System.out.println("************"+username);
		  String hql = "from User as u where u.username = '"+username+"'";
		  List<User> userList = (List<User>) this.hibernateTemplate.find(hql);
		  if(userList.size()>0){
		     flag = true;
		  }
		  System.out.println(flag);
		  return flag;
	}
	
}
