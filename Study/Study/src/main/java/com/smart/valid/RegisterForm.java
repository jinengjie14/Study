package com.smart.valid;

import java.util.UUID;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class RegisterForm {

	private String id = UUID.randomUUID().toString().replace("-", "");
	@NotEmpty(message="学号不能为空") @Size(min=9, max=15, message="学号长度为9~15位")
	private String student_id;
	@NotEmpty(message="用户名不能为空")
	private String uname;
	@NotEmpty(message="密码不能为空") @Size(min=6, max=30, message="密码长度为6~30位")
	private String passwd;
	@NotEmpty(message="确认密码不能为空")
	private String passwd2;
	@NotEmpty(message="电子邮箱不能为空") @Email(message="电子邮箱格式错误")
	private String mail;
	@NotEmpty(message="安全密码不能为空") @Size(min=6, max=6, message="安全密码长度为6位")
	private String safe_password;
	
	public String getSafe_password() {
		return safe_password;
	}
	public void setSafe_password(String safe_password) {
		this.safe_password = safe_password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getPasswd2() {
		return passwd2;
	}
	public void setPasswd2(String passwd2) {
		this.passwd2 = passwd2;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
}
