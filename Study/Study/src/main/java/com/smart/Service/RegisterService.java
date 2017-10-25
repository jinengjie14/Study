package com.smart.Service;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.BindingResult;




import com.smart.Entity.User;
import com.smart.Repository.UserDao;
import com.smart.utils.MD5;
import com.smart.valid.RegisterForm;

public class RegisterService {

	private int count = 0;
	private String Errorinfo;
	private UserDao userDao;
	public void init(BindingResult bindingResult, RegisterForm registerForm, UserDao userDao) {
		this.userDao=userDao;
		if(bindingResult.hasErrors()){
			setErrorInfo(bindingResult.getAllErrors().get(0).getDefaultMessage());
			this.plus();
			return;
		}
		checkPasswd(registerForm.getPasswd(), registerForm.getPasswd2());
		//checkUserName(registerForm.getUname());
		checkStudentId(registerForm.getStudent_id());
		checkEmail(registerForm.getMail());
		isNumeric(registerForm.getStudent_id());
		SafePwisNumeric(registerForm.getSafe_password());
	}
	


	public void isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
			   setErrorInfo("学号必须为纯数字");
			   this.plus();
		   } 
		}
	public void SafePwisNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
			   setErrorInfo("安全密码必须为纯数字");
			   this.plus();
		   } 
		}
	public void checkPasswd(String passwd,String confim_passwd) {
		if (!passwd.equals(confim_passwd)) {
			setErrorInfo("两次密码输入不一致");
			this.plus();
		}
	}
	public void checkStudentId(String student_id) {
		if (userDao.findByStudentIdExist(student_id)) {
			setErrorInfo("学号已存在,请仔细输入");
			this.plus();
		}
	}
	public void checkEmail(String email) {
		if (userDao.findByEmailExist(email)) {
			setErrorInfo("邮箱已存在,请仔细输入");
			this.plus();
		}
	}
//	public void checkUserName(String username) {
//		if (userDao.findByUserNameExist(username)) {
//			setErrorInfo("用户名已存在,请更换");
//			this.plus();
//		}
//	}
	
	
	public void Registercast(RegisterForm registerForm) { //字符串拼接以及注册。
		User user = new User();
		user.setId(registerForm.getId()); //用户ID
		user.setStudent_id(registerForm.getStudent_id());
		user.setUname(registerForm.getUname()); //用户账号
		user.setPasswd(MD5.stringMD5(registerForm.getPasswd())); //用户密码
		user.setSafe_password(MD5.stringMD5(registerForm.getSafe_password()));
		user.setMail(registerForm.getMail()); //用户邮箱
		user.setCtime(new Timestamp(System.currentTimeMillis()));
		userDao.SaveUser(user);
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
