package org.jyu.web.service.question.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.jyu.web.dao.question.QuestionLabelRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.service.question.QuestionLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionLabelServiceImpl implements QuestionLabelService {
	
	@Autowired
	private QuestionLabelRepository questionLabelDao;

	@Transactional
	@Override
	public Result save(QuestionLabel questionLabel) {
		questionLabelDao.saveAndFlush(questionLabel);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(QuestionLabel questionLabel) {
		questionLabelDao.saveAndFlush(questionLabel);
		return new Result(true, "更新成功");
	}

	@Transactional
	@Override
	public Result deleteById(String id) {
		questionLabelDao.deleteById(id);
		return new Result(true, "删除成功");
	}

	@Override
	public QuestionLabel findById(String id) {
		return questionLabelDao.getOne(id);
	}

	@Override
	public List<QuestionLabel> findByName(String name) {
		return questionLabelDao.findByName(name);
	}

	@Override
	public List<QuestionLabel> list() {
		return questionLabelDao.findAll();
	}

}
