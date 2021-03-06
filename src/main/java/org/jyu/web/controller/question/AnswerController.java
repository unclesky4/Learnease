package org.jyu.web.controller.question;

import org.jyu.web.dto.Result;
import org.jyu.web.service.question.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswerController {
	
	@Autowired
	private AnswerService service;
	
	/**
	 * 更新参考答案
	 * @param id    主键
	 * @param answerContent    答案
	 * @param analyse  分析
	 * @return   Result对象
	 */
	@RequestMapping(value="/answer/update", method=RequestMethod.POST)
	public Result update(String id, String answerContent, String analyse) {
		return service.update(id, answerContent, analyse);
	}
}
