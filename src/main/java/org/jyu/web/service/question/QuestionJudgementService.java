package org.jyu.web.service.question;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionJudgementJson;
import org.jyu.web.entity.question.QuestionJudgement;

public interface QuestionJudgementService {
	
	/**
	 * 保存判断题
	 * @param shortName 题目简述
	 * @param content  判断题主干
	 * @param difficulty  难度
	 * @param userId  提交人主键
	 * @param labelIds  标签主键
	 * @param answerContent  参考答案（对/错）
	 * @param analyse 参考答案分析
	 * @return
	 */
	Result save(String shortName, String content, Integer difficulty, String userId, List<String> labelIds, String answerContent, 
			String analyse);
	
	/**
	 * 
	 * @param id
	 * @param shortName 题目简述
	 * @param content  判断题主干
	 * @param difficulty  难度
	 * @param userId  提交人主键
	 * @param labelIds  标签主键
	 * @param answerContent  参考答案（对/错）
	 * @param analyse 参考答案分析
	 * @return
	 */
	Result update(String shortName, String id, String content, Integer difficulty, List<String> labelIds, String answerContent, 
			String analyse);
	
	Result deleteById(String id);
	
	QuestionJudgement findById(String id);
	
	Map<String, Object> list(int pageNumber, int pageSize, String sortOrder);
	
	Result judgeResult(String qid, String answercontent);
	
	/**
	 * 分页查询某个用户提交的判断题
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @param userId
	 * @return
	 */
	Map<String, Object> getPageByUser(int pageNumber, int pageSize, String sortOrder, String userId);

}
