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

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Entity
public class Teacher implements Serializable {

	private static final long serialVersionUID = -5734702131737958104L;
	
	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String tid;
	
	@Column(nullable=false, length=20)
	private String name;
	
	@Column(nullable=false, length=1)
	private String sex;
	
	@Column(nullable=false, length=10)
	private String idCard;  //职工号

	@Column(nullable=false, length=30)
	private String subject;  //学科
	
	@Column(nullable=false, length=15)
	private String phone;   //电话
	
	@Column(nullable=false)
	private Integer status;   //状态 0:未审核   1:已审核  2: 审核通过
	
	@Column(nullable=true, length = 20)
	@Enumerated(EnumType.STRING)
	private RoleEnum role = RoleEnum.Teacher;  //角色
	
	@OneToOne
	private User user;
	
	public Teacher() {}

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
