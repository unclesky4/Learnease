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
	
	/**
	 * 保存单选题
	 * @param shortName   主题/简述
	 * @param content   问题主干
	 * @param difficulty   难度
	 * @param options    选项（逗号分割）
	 * @param labelIds   问题标签主键（逗号分割）
	 * @param answerContent  参考答案内容
	 * @param analyse   参考答案分析
	 * @param userId   提交用户主键
	 * @return
	 */
	Result save(String shortName, String content, Integer difficulty, List<String> options, List<String> labelIds, 
			String answerContent, String analyse, String userId);
	
	
	Result update(String id, String shortName, String content, Integer difficulty, List<String> options, List<String> labelIds, 
			String answerContent, String analyse);
	
	Result deleteById(String id);
	
	QuestionSimple findById(String id);
	
	Map<String, Object> list(int pageNumber, int pageSize, String sortOrder);
	
	Result judgeResult(String qid, String solution);
	
	/**
	 * 分页查询某个用户提交的单选题
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @param userId
	 * @return
	 */
	Map<String, Object> getPageByUser(int pageNumber, int pageSize, String sortOrder, String userId);

}
