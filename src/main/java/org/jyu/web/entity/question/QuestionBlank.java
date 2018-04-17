package org.jyu.web.entity.question;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * 
 * @ClassName: QuestionBlank 
 * @Description: 填空题
 * @author: unclesky4
 * @date: Apr 1, 2018 12:30:35 PM
 */
@Entity
//@DiscriminatorValue("blank_q")
public class QuestionBlank extends Question{
	
	private static final long serialVersionUID = 3395193661267574894L;
	
	@OneToOne(cascade={CascadeType.ALL})
	private Answer answer;   //参考答案（答案用逗号分割）

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

}
