package org.jyu.web.service.question;

import java.util.List;

import org.jyu.web.dto.Result;
import org.jyu.web.entity.question.QuestionLabel;

public interface QuestionLabelService {
	
	Result save(QuestionLabel questionLabel);
	
	Result update(QuestionLabel questionLabel);
	
	Result deleteById(String id);
	
	QuestionLabel findById(String id);
	
	List<QuestionLabel> findByName(String name);
	
	List<QuestionLabel> list();

}
