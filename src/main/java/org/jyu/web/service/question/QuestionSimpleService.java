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
	 * @return  Result对象
	 */
	Result save(String shortName, String content, Integer difficulty, List<String> options, List<String> labelIds, 
			String answerContent, String analyse, String userId);
	
	
	/**
	 * 修改单选题
	 * @param id      主键
	 * @param shortName    简述
	 * @param content      题目
	 * @param difficulty   难度
	 * @param options      选项（逗号分割）
	 * @param labelIds     标签主键（逗号分割）
	 * @param answerContent  参考答案
	 * @param analyse    参考答案分析
	 * @return   Result对象
	 */
	Result update(String id, String shortName, String content, Integer difficulty, List<String> options, List<String> labelIds, 
			String answerContent, String analyse);
	
	/**
	 * 删除
	 * @param id    单选题主键
	 * @return    Result对象
	 */
	Result deleteById(String id);
	
	/**
	 * 通过主键查询
	 * @param id   单选题主键
	 * @return    QuestionSimple对象
	 */
	QuestionSimple findById(String id);
	
	
	/**
	 * 分页查询
	 * @param pageNumber   页码
	 * @param pageSize     分页大小
	 * @param sortOrder    排序
	 * @return   Map集合
	 */
	Map<String, Object> list(int pageNumber, int pageSize, String sortOrder);
	
	/**
	 * 评判用户提交的答案
	 * @param qid     单选题答案
	 * @param solution  答案内容
	 * @return   Result对象
	 */
	Result judgeResult(String qid, String solution);
	
	/**
	 * 分页查询某个用户提交的单选题
	 * @param pageNumber   页码
	 * @param pageSize     分页大小
	 * @param sortOrder    排序
	 * @param userId       用户主键
	 * @return   Map集合
	 */
	Map<String, Object> getPageByUser(int pageNumber, int pageSize, String sortOrder, String userId);

}
