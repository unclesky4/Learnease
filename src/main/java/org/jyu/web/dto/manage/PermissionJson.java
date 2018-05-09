package org.jyu.web.dto.manage;

public class PermissionJson {

	//主键
	private String id;
	
	//权限名
	private String name;
	
	//权限代码 or 页面跳转接口
	private String code;
	
	//是否为根目录
	private Boolean isRoot;
	
	//是否为目录
	private Boolean isCatalog;
	
	//是否显示为菜单
	private Boolean isMenuShow;
	
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
