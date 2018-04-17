package org.jyu.web.service.question;

import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionJudgementJson;

public interface QuestionJudgementService {
	
	/**
	 * 保存判断题
	 * @param shortName 题目简述
	 * @param content  判断题主干
	 * @param difficulty  难度
	 * @param userId  提交人主键
	 * @param labelId  标签主键
	 * @param answerContent  参考答案（对/错）
	 * @param analyse 参考答案分析
	 * @return
	 */
	Result save(String shortName, String content, Integer difficulty, String userId, String labelId, String answerContent, 
			String analyse);
	
	/**
	 * 
	 * @param id
	 * @param shortName 题目简述
	 * @param content  判断题主干
	 * @param difficulty  难度
	 * @param userId  提交人主键
	 * @param labelId  标签主键
	 * @param answerContent  参考答案（对/错）
	 * @param analyse 参考答案分析
	 * @return
	 */
	Result update(String shortName, String id, String content, Integer difficulty, String labelId, String answerContent, 
			String analyse);
	
	Result deleteById(String id);
	
	QuestionJudgementJson findById(String id);
	
	Map<String, Object> list(int pageNumber, int pageSize, String sortOrder);
	
	Result judgeResult(String qid, String answercontent);

}
