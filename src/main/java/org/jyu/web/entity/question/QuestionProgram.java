package org.jyu.web.entity.question;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
/**
 * 
 * @ClassName: QuestionProgram 
 * @Description: 编程题
 * @author: unclesky4
 * @date: Apr 1, 2018 12:32:02 PM
 */
@Entity
public class QuestionProgram extends Question{

	private static final long serialVersionUID = 5243064662837982172L;
	
	@Column(length=300, nullable=true)
	private String description;
	
	@Column(length=100, nullable=true)
	private String input;    //输入
	
	@Column(length=100, nullable=true)
	private String output;    //输出
	
	@Column(length=100, nullable=true)
	private String exampleInput;   //样例输入
	
	@Column(length=100, nullable=true)
	private String exampleOutput;   //样例输出
	
	@Column(length=200, nullable=true)
	private String hint;     //提示
	
	@OneToOne(cascade={CascadeType.ALL})
	private Answer answer;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getExampleInput() {
		return exampleInput;
	}

	public void setExampleInput(String exampleInput) {
		this.exampleInput = exampleInput;
	}

	public String getExampleOutput() {
		return exampleOutput;
	}

	public void setExampleOutput(String exampleOutput) {
		this.exampleOutput = exampleOutput;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

}
