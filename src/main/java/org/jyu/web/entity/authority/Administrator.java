package org.jyu.web.entity.authority;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.jyu.web.enums.RoleEnum;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Administrator implements Serializable {
	
	private static final long serialVersionUID = -3213658508824690374L;

	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String id;
	
	@Column(nullable=false, length=20)
	private String name; //姓名
	
	@JsonIgnore
	@Column(nullable=false, length=32)
	private String pwd;  //密码
	
	@Column(nullable=false, length=1)
	private String sex;  //性别
	
	@Column(nullable=true, length=15)
	private String phone;  //联系电话
	
	@Column(nullable=true, length=50)
	private String email;  //邮箱
	
	@Column(nullable=false, length=20)
	@Setter @Getter
	private String createTime;
	
	@Column(nullable=true, length = 20)
	@Enumerated(EnumType.STRING)
	private RoleEnum role;
	
	public Administrator() {}

	public Administrator(String id, String name, String pwd, String sex, String phone, String email) {
		super();
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.sex = sex;
		this.phone = phone;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}
}
