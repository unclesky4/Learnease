package org.jyu.web.dto;

public class ZtreeJson {
	
	private String id;    //主键
	
	private String name;  //节点名称
	
	private Boolean isParent;  //是否为父节点
	
	public ZtreeJson() {}

	public ZtreeJson(String id, String name, Boolean isParent) {
		this.id = id;
		this.name = name;
		this.isParent = isParent;
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

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

}
