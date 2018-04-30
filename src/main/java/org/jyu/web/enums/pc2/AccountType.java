package org.jyu.web.enums.pc2;

/**
 * pc2帐号类型
 * @author unclesky4 28/09/2017
 *
 */
public enum AccountType {

	JUDGE("judge","拥有判题的权限"),
	
	ADMINISTRATOR("administrator","拥有添加帐号、编程语言、提交题目的权限");
	
	private String type;
	
	private String description;
	
	AccountType(String type, String description) {
		this.type = type;
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
