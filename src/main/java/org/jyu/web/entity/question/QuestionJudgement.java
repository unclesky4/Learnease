package org.jyu.web.entity.question;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class QuestionJudgement extends Question {

	private static final long serialVersionUID = 5748599943140318613L;

	@OneToMany(cascade={CascadeType.ALL})
	private List<Option> judgementOptions;
	
	@OneToOne(cascade={CascadeType.ALL})
	private Answer answer;

	public List<Option> getJudgementOptions() {
		return judgementOptions;
	}

	public void setJudgementOptions(List<Option> judgementOptions) {
		this.judgementOptions = judgementOptions;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
}
