package org.jyu.web.entity.question;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * @ClassName: QuestionSimple 
 * @Description: 单选题
 * @author: unclesky4
 * @date: Apr 1, 2018 12:25:36 PM
 */
@Entity
public class QuestionSimple extends Question {

	private static final long serialVersionUID = -8825219068930650937L;

	@OneToOne(cascade={CascadeType.ALL})
	private Answer simpleAnswer;
	
	@OneToMany(cascade={CascadeType.ALL})
	private List<Option> simpleOptions;

	public Answer getSimpleAnswer() {
		return simpleAnswer;
	}

	public void setSimpleAnswer(Answer simpleAnswer) {
		this.simpleAnswer = simpleAnswer;
	}

	public List<Option> getSimpleOptions() {
		return simpleOptions;
	}

	public void setSimpleOptions(List<Option> simpleOptions) {
		this.simpleOptions = simpleOptions;
	}


}
