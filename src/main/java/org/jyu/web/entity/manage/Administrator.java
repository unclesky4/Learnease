package org.jyu.web.entity.manage;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

/**
 * @ClassName: Administrator 
 * @Description: 管理员
 * @author: unclesky4
 * @date: May 6, 2018 5:13:14 PM
 */
@Entity
public class Administrator implements Serializable {
	
	private static final long serialVersionUID = -3213658508824690374L;

	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String id;
	
	//姓名
	@Column(nullable=false, length=20, unique=true)
	private String name;
	
	//密码
	@Column(nullable=false, length=32)
	private String pwd;
	
	//性别
	@Column(nullable=true, length=1)
	private String sex;
	
	//联系电话
	@Column(nullable=true, length=15)
	private String phone;
	
	//邮箱
	@Column(nullable=true, length=50)
	private String email;
	
	@Column(nullable=false, length=20, updatable=false)
	private String createTime;
	
	//角色
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.MERGE, CascadeType.REFRESH})
	private List<Role> roles;
	
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
