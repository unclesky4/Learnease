package org.jyu.web.dto.pc2;

/**
 * 用于PC2的Problem转为JSON格式传到前台
 * @author unclesky4 10/10/2017
 *
 */
public class JsonProblem {

	private String shortName;
	
	private String active;
	
	private String readInputDataFromSTDIN;
	
	private String validatedProblem;
	
	private String usingPC2Validator;

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getReadInputDataFromSTDIN() {
		return readInputDataFromSTDIN;
	}

	public void setReadInputDataFromSTDIN(String readInputDataFromSTDIN) {
		this.readInputDataFromSTDIN = readInputDataFromSTDIN;
	}

	public String getValidatedProblem() {
		return validatedProblem;
	}

	public void setValidatedProblem(String validatedProblem) {
		this.validatedProblem = validatedProblem;
	}

	public String getUsingPC2Validator() {
		return usingPC2Validator;
	}

	public void setUsingPC2Validator(String usingPC2Validator) {
		this.usingPC2Validator = usingPC2Validator;
	}

	@Override
	public String toString() {
		return "[shortName=" + shortName + ", active=" + active + ", readInputDataFromSTDIN="
				+ readInputDataFromSTDIN + ", validatedProblem=" + validatedProblem + ", usingPC2Validator="
				+ usingPC2Validator + "]";
	}
	

}
