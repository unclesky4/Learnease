package org.jyu.web.enums;

/**
 * @ClassName: SolutionStatus 
 * @Description: 学生提交答案的状态
 * @author: unclesky4
 * @date: Apr 1, 2018 10:18:25 AM
 */
public enum SolutionStatus {
	
	TEMPORARY("暂存"),
	COMPLETE("确认提交");
	
	private String description;   //状态描述
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private SolutionStatus(String description) {
		this.description = description;
	}
}
