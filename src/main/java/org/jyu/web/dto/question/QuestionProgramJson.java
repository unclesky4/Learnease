package org.jyu.web.dto.question;

public class QuestionProgramJson {

	private String id;   //主键
	
	private String shortName;  //题目简述
	
	private String content;  //编程题主干
	
	private Integer difficulty;  //难度
	
	private String description;  //题目描述
	
	private String input;  //输入
	
	private String output;  //输出
	
	private String exampleInput;  //示例输入
	
	private String exampleOutput;  //示例输出
	
	private String hint;  //提示
	
	private String label;  //标签
	
	private String authorId;  //提交人主键
	
	private String authorName;  //提交人昵称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
}
