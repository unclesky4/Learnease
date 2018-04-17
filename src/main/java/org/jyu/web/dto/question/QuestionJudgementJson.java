package org.jyu.web.dto.question;


import java.util.List;

import org.jyu.web.entity.question.Option;
import org.jyu.web.enums.QuestionType;

public class QuestionJudgementJson {

	private String id;
	
	private String content;   //问题主干
	
	private Integer difficulty;  //难度
	
	private String createTime;   //创建时间
	
	private QuestionType type;  //题目类型
	
	private String authorId;  //提交人主键
	
	private String authorName;  //提交人昵称
	
	private List<Option> options;  //选项

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
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

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
}
