package org.jyu.web.service.question;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.entity.question.QuestionMultiple;

public interface QuestionMultipleService {
	
	Result save(String shortName,String content,Integer difficulty, List<String> labelIds, String answerContent, 
			String analyse, String options, String userId);
	
	Result update(String id, String shortName,String content,Integer difficulty, List<String> labelIds, String answerContent, 
			String analyse, String options);
	
	Result delete(String id);
	
	QuestionMultiple findById(String id);
	
	Map<String, Object> list(int pageNumber, int pageSize, String sortOrder);

}
