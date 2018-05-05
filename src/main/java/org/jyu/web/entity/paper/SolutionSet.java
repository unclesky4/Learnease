package org.jyu.web.entity.paper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.jyu.web.entity.question.Solution;
import org.jyu.web.enums.paper.SolutionSetStatus;


/**
 * @ClassName: SolutionSet 
 * @Description: 用户提交的试卷答案
 * @author: unclesky4
 * @date: May 1, 2018 1:55:53 PM
 */
@Entity
public class SolutionSet implements Serializable{

	private static final long serialVersionUID = 8352500621389239971L;
	
	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String id;
	
	//考试总分
	private Float totalScore;
	
	//答卷的状态
	@Column(nullable=true, length = 20)
	@Enumerated(EnumType.STRING)
	private SolutionSetStatus status;
	
	//每道题的答案
	@ElementCollection(fetch=FetchType.LAZY)
	private Map<String, Solution> questionSolution =  new HashMap<>();
	
	//每道题的得分
	@ElementCollection(fetch=FetchType.LAZY)
	private Map<String, Integer> questionAccount =  new HashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Float totalScore) {
		this.totalScore = totalScore;
	}

	public SolutionSetStatus getStatus() {
		return status;
	}

	public void setStatus(SolutionSetStatus status) {
		this.status = status;
	}

	public Map<String, Solution> getQuestionSolution() {
		return questionSolution;
	}

	public void setQuestionSolution(Map<String, Solution> questionSolution) {
		this.questionSolution = questionSolution;
	}

	public Map<String, Integer> getQuestionAccount() {
		return questionAccount;
	}

	public void setQuestionAccount(Map<String, Integer> questionAccount) {
		this.questionAccount = questionAccount;
	}

}
