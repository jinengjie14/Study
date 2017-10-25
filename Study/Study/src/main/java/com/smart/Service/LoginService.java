package com.smart.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;
import org.springframework.validation.BindingResult;

import com.smart.Entity.User;
import com.smart.Repository.UserDao;
import com.smart.valid.LoginForm;



public class LoginService {
	private int count = 0;
	private String Errorinfo;
	UserDao userDao;
	public void init(BindingResult bindingResult, LoginForm loginform, HttpSession httpSession,UserDao userDao) {
		this.userDao=userDao;
		if(bindingResult.hasErrors()){
			setErrorInfo(bindingResult.getAllErrors().get(0).getDefaultMessage());
			this.plus();
			return;
		}
		checkUserName(loginform.getStudent_id());
		isNumeric(loginform.getStudent_id());
		if(this.count==0){
			User user = new User();
			user=userDao.checkPasswd(loginform.getStudent_id(),loginform.getPasswd());
			if(user.getId()==null){
				this.plus();
				setErrorInfo("用户名或密码错误,请重试!");
			}else{
				//httpSession.setAttribute("userid", user.getId());
				httpSession.setAttribute("user", user);
			}
		}
		
	}
	
	public void checkUserName(String Student_id) {
		if (!userDao.findByUserNameExist(Student_id)) {
			setErrorInfo("该用户名还未注册!");
			this.plus();
		}
	}
	
	public void isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
			   setErrorInfo("学号必须为纯数字");
			   this.plus();
		   } 
		}
	public User Login(LoginForm loginform) { //登录。
		User user = new User();
		user=userDao.checkPasswd(loginform.getStudent_id(),loginform.getPasswd());
		return user;
	}
	
	public void plus() {
		this.count++;
	}
		
	public int getCount() {
		return this.count;
	}
	public String getErrorInfo(){
		return this.Errorinfo;
	}
	
	public void setErrorInfo(String error){
		this.Errorinfo=error;
	}
}