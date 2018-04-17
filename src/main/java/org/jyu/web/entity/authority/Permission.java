package org.jyu.web.entity.authority;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @ClassName: Permission  
 * @Description: 权限
 * @author unclesky4 
 * @date Mar 25, 2018 11:26:33 AM 
 *
 */
@Entity
public class Permission implements Serializable {

	private static final long serialVersionUID = -1558107333678643836L;

	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String id;
	
	@Column(nullable=false, length=20)
	private String name;   //权限名
	
	@Column(nullable=true, length=50)
	private String code;   //权限代码 or 页面跳转接口
	
	private Boolean isRoot;  //是否为根目录
	
	private Boolean isCatalog;  //是否为目录
	
	private Boolean isMenuShow;  //是否显示为菜单
	
	@JsonIgnore
	@OneToOne(cascade={CascadeType.MERGE,CascadeType.REMOVE})
	private Permission pid;   //父权限

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(Boolean isRoot) {
		this.isRoot = isRoot;
	}

	public Boolean getIsCatalog() {
		return isCatalog;
	}

	public void setIsCatalog(Boolean isCatalog) {
		this.isCatalog = isCatalog;
	}

	public Boolean getIsMenuShow() {
		return isMenuShow;
	}

	public void setIsMenuShow(Boolean isMenuShow) {
		this.isMenuShow = isMenuShow;
	}

	public Permission getPid() {
		return pid;
	}

	public void setPid(Permission pid) {
		this.pid = pid;
	}
}
