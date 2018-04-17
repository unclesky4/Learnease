package org.jyu.web.entity.question;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.jyu.web.entity.authority.User;
import org.jyu.web.enums.SolutionStatus;

/**
 * @ClassName: Solution 
 * @Description: 学生提交的答案
 * @author: unclesky4
 * @date: Apr 1, 2018 10:14:50 AM
 */
@Entity
public class Solution implements Serializable {

	private static final long serialVersionUID = 3794661283527777791L;
	
	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String id;   //主键
	
	@Column(nullable=false, length=512)
	private String content;    //答案内容
	
	@Column(nullable=false, length = 20)
	@Enumerated(EnumType.STRING)
	private SolutionStatus status;   //提交状态
	
	private Date submitTime;    //提交时间
	
	@OneToOne
	private Question question;    //关联问题
	
	@ManyToOne
	private User submitter;
	
	public Solution() {}

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

	public SolutionStatus getStatus() {
		return status;
	}

	public void setStatus(SolutionStatus status) {
		this.status = status;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public User getStudent() {
		return submitter;
	}

	public void setStudent(User submitter) {
		this.submitter = submitter;
	}

}
