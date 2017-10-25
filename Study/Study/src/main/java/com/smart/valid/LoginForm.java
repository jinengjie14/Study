package com.smart.valid;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginForm {
	@NotEmpty(message="用户名不能为空")
	private String student_id;
	@NotEmpty(message="密码不能为空")
	private String passwd;

	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
