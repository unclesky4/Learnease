package org.jyu.web.service.question;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
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
	 * @return  Result对象
	 */
	Result save(String shortName, String content, Integer difficulty, String userId, List<String> labelIds, String answerContent, 
			String analyse);
	
	/**
	 * 修改判断题
	 * @param id   主键
	 * @param shortName 题目简述
	 * @param content  判断题主干
	 * @param difficulty  难度
	 * @param labelIds  标签主键List
	 * @param answerContent  参考答案（对/错）
	 * @param analyse 参考答案分析
	 * @return  Result对象
	 */
	Result update(String id, String shortName, String content, Integer difficulty, List<String> labelIds, String answerContent, 
			String analyse);
	
	/**
	 * 删除判断题
	 * @param id   主键
	 * @return  Result对象
	 */
	Result deleteById(String id);
	
	/**
	 * 通过主键查询
	 * @param id   主键
	 * @return   QuestionJudgementd对象
	 */
	QuestionJudgement findById(String id);
	
	/**
	 * 分页查询
	 * @param pageNumber   页码
	 * @param pageSize     分页大小
	 * @param sortOrder    排序
	 * @return  Map集合
	 */
	Map<String, Object> list(int pageNumber, int pageSize, String sortOrder);
	
	
	/**
	 * 判断提交的答案
	 * @param qid     判断题主键
	 * @param answercontent    答案内容
	 * @return   Result对象
	 */
	Result judgeResult(String qid, String answercontent);
	
	/**
	 * 分页查询某个用户提交的判断题
	 * @param pageNumber    页码
	 * @param pageSize      分页大小
	 * @param sortOrder     排序
	 * @param userId        用户主键
	 * @return   Map对象
	 */
	Map<String, Object> getPageByUser(int pageNumber, int pageSize, String sortOrder, String userId);

}
