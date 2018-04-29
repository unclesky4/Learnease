package org.jyu.web.service.question;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.entity.question.QuestionProgram;

public interface QuestionProgramService {

	Result save(String shortName, String content, Integer difficulty, String description, String input, String output,
			String exampleInput, String exampleOutput, String hint, String answerContent, String analyse, List<String> labelIds,
			String userId);

	Result update(String id, String shortName, String content, Integer difficulty, String description, String input,
			String output, String exampleInput, String exampleOutput, String hint, String answerContent, String analyse,
			List<String> labelIds);

	Result delete(String id);

	QuestionProgram findById(String id);

	Map<String, Object> list(int pageNumber, int pageSize, String sortOrder);

	Result judgeResult(String qid, String solution);

	/**
	 * 分页查询某个用户提交的编程题
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @param userId
	 * @return
	 */
	Map<String, Object> getPageByUser(int pageNumber, int pageSize, String sortOrder, String userId);
}
