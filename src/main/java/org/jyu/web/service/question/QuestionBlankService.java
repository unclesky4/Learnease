package org.jyu.web.service.question;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.entity.question.QuestionBlank;

public interface QuestionBlankService {
	
	Result save(String shortName, String content, Integer difficulty, String userId, List<String> labelIds, String answerContent, 
			String analyse);
	
	Result update(String id, String shortName, String content, Integer difficulty, List<String> labelIds, String answerContent, 
			String analyse);
	
	Result deleteById(String id);
	
	QuestionBlank findById(String id);
	
	Map<String, Object> list(int pageNumber, int pageSize, String sortOrder);
	
	Result judgeResult(String qid, String solution);

	/**
	 * 分页查询某个用户提交的填空题
	 * @param pageNumber   页码
	 * @param pageSize     分页大小
	 * @param sortOrder    排序
	 * @param userId       用户主键
	 * @return  Map集合
	 */
	Map<String, Object> getPageByUser(int pageNumber, int pageSize, String sortOrder, String userId);
}
