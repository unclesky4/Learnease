package org.jyu.web.entity.authority;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.jyu.web.enums.RoleEnum;

/**
 * 
 * @ClassName: Student  
 * @Description: 学生
 * @author unclesky4 
 * @date Mar 25, 2018 10:02:14 AM 
 *
 */
@Entity
public class Student implements Serializable {
	
	private static final long serialVersionUID = 8881246013547629926L;

	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String stuId;

	@Column(nullable=false, length=10)
	private String idCard;  //学号
	
	@Column(nullable=false, length=20)
	private String stuName;   //学生姓名
	
	@Column(nullable=false, length=1)
	private String stuSex;     //学生性别
	
	@Column(nullable=false, length=100)
	private String stuAcademy;   //学院
	
	@Column(nullable=false, length=30)
	private String stuClass;   //专业班级
	
	@Column(nullable=false, length=4)
	private Integer stuEntranceTime;    //入学年份
	
	@Column(nullable=false)
	private Integer status;   //状态 0:未审核   1:已审核
	
	@Column(nullable=true, length = 20)
	@Enumerated(EnumType.STRING)
	private RoleEnum role;    //角色
	
	@OneToOne
	private User user;
	
	public Student() {}

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

	public String getStuAcademy() {
		return stuAcademy;
	}

	public void setStuAcademy(String stuAcademy) {
		this.stuAcademy = stuAcademy;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
