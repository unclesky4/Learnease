package org.jyu.web.entity.paper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.jyu.web.entity.authority.User;
import org.jyu.web.entity.question.Question;

/**
 * @ClassName: Papers 
 * @Description: 试卷
 * @author: unclesky4
 * @date: May 1, 2018 1:15:56 PM
 */
@Entity
public class Paper implements Serializable {

	private static final long serialVersionUID = 2480613019403046713L;
	
	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String id;
	
	//试卷名
	@Column(nullable=false, length=100, unique=true)
	private String name;
	
	//是否公开  0：私有，1：公开
	private Integer status;
	
	//创建时间
	@Column(nullable=false, length=22)
	private String createDate;
	
	//创建者
	@ManyToOne(cascade={CascadeType.DETACH})
	private User author;
	
	//题目
	@ManyToMany(cascade={CascadeType.DETACH, CascadeType.MERGE})
	private List<Question> questionset = new ArrayList<>();
	
	//每道题的分值
	@ElementCollection(fetch=FetchType.LAZY)
	private Map<String, Integer> questionScore = new HashMap<>();
	
	//试卷标签
	@ManyToMany
	private List<PaperLabel> paperLabels;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<Question> getQuestionset() {
		return questionset;
	}

	public void setQuestionset(List<Question> questionset) {
		this.questionset = questionset;
	}

	public Map<String, Integer> getQuestionScore() {
		return questionScore;
	}

	public void setQuestionScore(Map<String, Integer> questionScore) {
		this.questionScore = questionScore;
	}

	public List<PaperLabel> getPaperLabels() {
		return paperLabels;
	}

	public void setPaperLabels(List<PaperLabel> paperLabels) {
		this.paperLabels = paperLabels;
	}

}
