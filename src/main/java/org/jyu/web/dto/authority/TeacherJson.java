package org.jyu.web.dto.authority;

import java.io.Serializable;

public class TeacherJson implements Serializable{

	private static final long serialVersionUID = 4391584472841248706L;

	private String tid;
	
	private String name;
	
	private String sex;
	
	private String idCard;  //职工号

	private String subject;  //学科
	
	private String phone;   //电话
	
	private String status;   //审核状态 0:待审核   1:审核通过  2: 审核不通过
	
	private String userId;   //用户主键
	
	private String userName;  //用户昵称
	
	private String userEmail;  //用户邮箱

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
