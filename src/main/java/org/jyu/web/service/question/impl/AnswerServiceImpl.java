package org.jyu.web.service.question.impl;

import org.jyu.web.dao.question.AnswerRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.entity.question.Answer;
import org.jyu.web.service.question.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerServiceImpl implements AnswerService {
	
	@Autowired
	private AnswerRepository answerDao;
	
	@Transactional
	@Override
	public Result update(String id, String answerContent, String analyse) {
		Answer answer = answerDao.getOne(id);
		if (answer == null) {
			return new Result(false, "对象不存在");
		}
		if (answerContent != null && answerContent != "") {
			answer.setContent(answerContent);
		}
		if (analyse != null && analyse != "") {
			answer.setAnalyse(analyse);
		}
		answerDao.saveAndFlush(answer);
		return new Result(true, "修改成功");
	}
}
