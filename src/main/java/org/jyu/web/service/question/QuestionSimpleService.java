package org.jyu.web.service.question;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.entity.question.QuestionSimple;

/**
 * 
 * @ClassName: QuestionSimpleService 
 * @Description: 单选题-业务层
 * @author: unclesky4
 * @date: Apr 1, 2018 4:18:16 PM
 */
public interface QuestionSimpleService {
	
	Result save(String shortName, String content, Integer difficulty, List<String> options, List<String> labelIds, 
			String answerContent, String analyse, String userId);
	
	Result update(String id, String shortName, String content, Integer difficulty, List<String> options, List<String> labelIds, 
			String answerContent, String analyse);
	
	Result deleteById(String id);
	
	QuestionSimple findById(String id);
	
	Map<String, Object> list(int pageNumber, int pageSize, String sortOrder);
	
	Result judgeResult(String qid, String solution);

}
