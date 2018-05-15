package org.jyu.web.dto;

public class ZtreeJson {
	
	private String id;    //主键
	
	private String name;  //节点名称
	
	private String pId;   //父节点
	
	private Boolean checked; //是否选中

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

}
