package org.jyu.web.dto.authority;

import java.io.Serializable;

public class UserJson implements Serializable{

	private static final long serialVersionUID = -5887728812969812135L;

	private String uid;
	
	private String name;    //昵称
	
	private String email;    //邮箱帐号
	
	private String validation;    //邮箱验证状态
	
	private String registerTime; //注册时间

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
}
