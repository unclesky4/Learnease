package org.jyu.web.entity.question;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @ClassName: QuestionLabel 
 * @Description: 问题标签
 * @author: unclesky4
 * @date: Apr 1, 2018 10:00:37 AM
 */
@Entity
public class QuestionLabel implements Serializable {
	
	private static final long serialVersionUID = -5095660055271932970L;

	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String id;   //主键
	
	@Column(nullable=false, length=20, unique=true)
	private String name;  //标签名
	
	@Column(nullable=false, length=20, updatable=false)
	private String createTime;
	
	@ManyToMany(mappedBy="questionLabels", fetch=FetchType.LAZY)
	private List<Question> questions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
