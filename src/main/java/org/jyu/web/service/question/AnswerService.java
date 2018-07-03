package org.jyu.web.service.question;

import org.jyu.web.dto.Result;

public interface AnswerService {
	
	/**
	 * 更新参考答案
	 * @param id   主键
	 * @param answerContent   参考答案内容
	 * @param analyse    分析
	 * @return  Result对象
	 */
	Result update(String id, String answerContent, String analyse);

}
