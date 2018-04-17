package org.jyu.web.service.question;

import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionProgramJson;

public interface QuestionProgramService {

	Result save(String shortName, String content, Integer difficulty, String description, String input, String output,
			String exampleInput, String exampleOutput, String hint, String answerContent, String analyse, String labelId,
			String userId);

	Result update(String id, String shortName, String content, Integer difficulty, String description, String input,
			String output, String exampleInput, String exampleOutput, String hint, String answerContent, String analyse,
			String labelId);

	Result delete(String id);

	QuestionProgramJson findById(String id);

	Map<String, Object> list(int pageNumber, int pageSize, String sortOrder);

	Result judgeResult(String qid, String solution);

}
