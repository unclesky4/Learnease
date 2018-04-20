package org.jyu.web.entity.question;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

/**
 * 
 * @ClassName: QuestionMultiple 
 * @Description: 多选题
 * @author: unclesky4
 * @date: Apr 1, 2018 12:24:54 PM
 */
@Entity
public class QuestionMultiple extends Question{

	private static final long serialVersionUID = 6272012706788038813L;
	
	@ManyToMany(cascade={CascadeType.ALL})
	private List<Option> options;   //选项
	
	@OneToOne(cascade={CascadeType.ALL})
	private Answer answer;   //参考答案

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

}
