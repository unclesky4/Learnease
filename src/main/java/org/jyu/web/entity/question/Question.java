package org.jyu.web.entity.question;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.jyu.web.entity.authority.User;
import org.jyu.web.enums.QuestionType;

/**
 * 
 * @ClassName: Question 
 * @Description: 所有问题的父类
 * @author: unclesky4
 * @date: Apr 1, 2018 10:01:18 AM
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Question implements Serializable {

	private static final long serialVersionUID = 5138724098326650888L;
	
	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String id;
	
	@Column(length=20, nullable=false)
	private String shortName;  //题目简述
	
	@Column(length=4096, nullable=false)
	private String content;   //问题主干
	
	@Column(length=1, nullable=false)
	private Integer difficulty;  //难度
	
	@Column(length=20, nullable=false)
	private String createTime;   //创建时间
	
	@Column(nullable=true, length = 20)
	@Enumerated(EnumType.STRING)
	private QuestionType type;
	
	@ManyToOne
	private User author;  //提交人
	
	@ManyToMany
	private List<QuestionLabel> questionLabels;

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

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
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

	public List<QuestionLabel> getQuestionLabels() {
		return questionLabels;
	}

	public void setQuestionLabels(List<QuestionLabel> questionLabels) {
		this.questionLabels = questionLabels;
	}

}
