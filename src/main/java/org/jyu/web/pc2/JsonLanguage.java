package org.jyu.web.pc2;


/**
 * 用于PC2的Language转为JSON格式传到前台
 * @author unclesky4 10/10/2017
 *
 */
public class JsonLanguage {

	private String displayName;
	
	private String compilerCommandLine;
	
	private String executeCommandLine;
	
	private String judgesCommandLine;
	
	public JsonLanguage() {}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getCompilerCommandLine() {
		return compilerCommandLine;
	}

	public void setCompilerCommandLine(String compilerCommandLine) {
		this.compilerCommandLine = compilerCommandLine;
	}

	public String getExecuteCommandLine() {
		return executeCommandLine;
	}

	public void setExecuteCommandLine(String executeCommandLine) {
		this.executeCommandLine = executeCommandLine;
	}

	public String getJudgesCommandLine() {
		return judgesCommandLine;
	}

	public void setJudgesCommandLine(String judgesCommandLine) {
		this.judgesCommandLine = judgesCommandLine;
	}

}
