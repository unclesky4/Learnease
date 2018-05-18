package org.jyu.web.entity.question;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @ClassName: Answer 
 * @Description: 参考答案
 * @author: unclesky4
 * @date: Apr 1, 2018 10:09:32 AM
 */
@Entity
public class Answer implements Serializable{

	private static final long serialVersionUID = -3251942794341532069L;
	
	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String id;
	
	@Column(nullable=true, length=4096)
	private String content;
	
	@Column(nullable=true, length=2048)
	private String analyse;        //分析

	public Answer() {
	}
	
	public Answer(String analyse, String content) {
		this.analyse = analyse;
		this.content = content;
	}

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

	public String getAnalyse() {
		return analyse;
	}

	public void setAnalyse(String analyse) {
		this.analyse = analyse;
	}
}