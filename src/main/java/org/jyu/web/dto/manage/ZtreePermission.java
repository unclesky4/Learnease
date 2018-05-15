package org.jyu.web.dto.manage;

/**
 * @ClassName: ZtreeJson 
 * @Description:  权限信息 - 针对ZTree
 * @author: unclesky4
 * @date: May 10, 2018 7:27:19 PM
 */
public class ZtreePermission {
	
	private String id;    //主键
	
	private String name;  //节点名称
	
	private String pId;   //父节点
	
	private Boolean checked; //是否选中
	
	private Boolean isCatalog; //是否为目录
	
	private String code;   //权限代码
	
	public ZtreePermission() {}

	public ZtreePermission(String id, String name, Boolean isParent) {
		this.id = id;
		this.name = name;
		//this.isParent = isParent;
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

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getIsCatalog() {
		return isCatalog;
	}

	public void setIsCatalog(Boolean isCatalog) {
		this.isCatalog = isCatalog;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
