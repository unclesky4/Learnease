package org.jyu.web.service.manage;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.manage.LabelJson;
import org.jyu.web.entity.question.QuestionLabel;

public interface QuestionLabelService {
	
	Result save(String name);
	
	Result update(String id, String name);
	
	Result deleteById(String id);
	
	QuestionLabel findById(String id);
	
	List<LabelJson> list();
	
	Map<String, Object> pageJson(Integer pageNumber, Integer pageSize);

}
