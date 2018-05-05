package org.jyu.web.entity.authority;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @ClassName: Role  
 * @Description: 角色
 * @author unclesky4 
 * @date Mar 25, 2018 11:23:12 AM 
 *
 */
@Entity
public class Role implements Serializable {
	
	private static final long serialVersionUID = -7364824145264957359L;

	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String id;
	
	@Column(length=20, nullable=false)
	private String name;   //角色名称
	
	@Column(length=20, nullable=false, unique=true)
	private String code;  //角色代码
	
	@ManyToMany
	private List<Permission> permissions;   //权限
	
	public Role() {}

	public Role(String id, String name, String code, List<Permission> permissions) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.permissions = permissions;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
}
