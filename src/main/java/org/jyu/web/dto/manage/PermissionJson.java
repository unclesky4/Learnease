package org.jyu.web.dto.manage;

public class PermissionJson {

	//主键
	private String id;
	
	//权限名
	private String name;
	
	//权限代码 or 页面跳转接口
	private String code;
	
	//启用状态
	private Boolean status;
	
	//父权限主键
	private String pid;

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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
