package org.jyu.web.enums.paper;

public enum SolutionSetStatus {
	EDITING("正在做题"),
	SUBMITTING("已提交"),
	SCORING("正在打分"),
	SCORED("已打分"),
	PUBLISHED("表示公布");
	
	private String description;
	
	SolutionSetStatus(String description) {
		this.setDescription(description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
