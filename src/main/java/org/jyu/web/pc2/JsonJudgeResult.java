package org.jyu.web.pc2;

/**
 * 用于转成JSON格式传到前台
 * @author unclesky4 10/10/2017
 *
 */
public class JsonJudgeResult {

	private String stuId = "";
	
	private String problemTopic = "";
	
	private String languageDisplayName = "";
	
	private String solution = "";
	
	//true表示程序准备好运行
	private boolean status = true;
	
	//未准备好运行的原因
	private String failInfo = "";

	//status为false时judgeResullt为null
	private JudgeResult judgeResult = null;
	
	public JsonJudgeResult() {}
	
	public JsonJudgeResult(String stuId, String problemTopic, String languageDisplayName) {
		this.stuId = stuId;
		this.problemTopic = problemTopic;
		this.languageDisplayName = languageDisplayName;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getProblemTopic() {
		return problemTopic;
	}

	public void setProblemTopic(String problemTopic) {
		this.problemTopic = problemTopic;
	}

	public String getLanguageDisplayName() {
		return languageDisplayName;
	}

	public void setLanguageDisplayName(String languageDisplayName) {
		this.languageDisplayName = languageDisplayName;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getFailInfo() {
		return failInfo;
	}

	public void setFailInfo(String failInfo) {
		this.failInfo = failInfo;
	}

	public JudgeResult getJudgeResult() {
		return judgeResult;
	}

	public void setJudgeResult(JudgeResult judgeResult) {
		this.judgeResult = judgeResult;
	}
}
