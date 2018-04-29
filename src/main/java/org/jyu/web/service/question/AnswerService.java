package org.jyu.web.service.question;

import org.jyu.web.dto.Result;

public interface AnswerService {
	
	/**
	 * 更新参考答案
	 * @param id
	 * @param answerContent
	 * @param analyse
	 * @return
	 */
	Result update(String id, String answerContent, String analyse);

}
