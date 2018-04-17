package org.jyu.web.dto.authority;

import java.io.Serializable;

public class StudentJson implements Serializable{

	private static final long serialVersionUID = 5091226723584793454L;

	private String stuId;

	private String idCard;  //学号
	
	private String stuName;   //学生姓名
	
	private String stuSex;     //学生性别
	
	private String stuClass;   //专业班级
	
	private Integer stuEntranceTime;    //入学年份
	
	private Integer status_code; //状态码
	
	private String status;   //状态 0:未审核   1:已审核
	
	private String stuAcademy;  //学院名称
	
	private String userId;   //用户主键
	
	private String userName;  //用户昵称
	
	private String userEmail;  //用户邮箱

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getStuSex() {
		return stuSex;
	}

	public void setStuSex(String stuSex) {
		this.stuSex = stuSex;
	}

	public String getStuClass() {
		return stuClass;
	}

	public void setStuClass(String stuClass) {
		this.stuClass = stuClass;
	}

	public Integer getStuEntranceTime() {
		return stuEntranceTime;
	}

	public void setStuEntranceTime(Integer stuEntranceTime) {
		this.stuEntranceTime = stuEntranceTime;
	}

	public Integer getStatus_code() {
		return status_code;
	}

	public void setStatus_code(Integer status_code) {
		this.status_code = status_code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStuAcademy() {
		return stuAcademy;
	}

	public void setStuAcademy(String stuAcademy) {
		this.stuAcademy = stuAcademy;
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
