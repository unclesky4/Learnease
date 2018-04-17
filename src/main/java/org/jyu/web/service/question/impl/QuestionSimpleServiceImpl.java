package org.jyu.web.service.question.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.jyu.web.dao.question.QuestionLabelRepository;
import org.jyu.web.dao.question.QuestionSimpleRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionSimpleJson;
import org.jyu.web.entity.authority.User;
import org.jyu.web.entity.question.Answer;
import org.jyu.web.entity.question.Option;
import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.entity.question.QuestionSimple;
import org.jyu.web.service.question.QuestionSimpleService;
import org.jyu.web.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;


@Service
public class QuestionSimpleServiceImpl implements QuestionSimpleService {
	
	@Autowired
	private QuestionSimpleRepository questionSimpleDao;
	
	@Autowired
	private QuestionLabelRepository questionLabelDao;

	@Transactional
	@Override
	public Result save(String shortName, String content, Integer difficulty, String options, String labelId, 
			String answerContent, String analyse) {
		//单选题对象
		QuestionSimple questionSimple = new QuestionSimple();
		questionSimple.setShortName(shortName);
		questionSimple.setContent(content);
		questionSimple.setCreateTime(DateUtil.DateToString(DateUtil.YMDHMS, new Date()));
		Session session = SecurityUtils.getSubject().getSession();
		User user = (User) session.getAttribute("user");
		if(user != null && user.getTeacher() != null) {
			questionSimple.setAuthor(user);
		}
		//答案
		Answer answer = new Answer(analyse, answerContent);
		questionSimple.setSimpleAnswer(answer);
		
		//选项
		List<Option> options_list = new ArrayList<>();
		String[] option_array = options.split(",");
		for (int i = 0; i < option_array.length; i++) {
			if(option_array[i] == "") {continue;}
			Option option = new Option(option_array[i]);
			options_list.add(option);
		}
		questionSimple.setSimpleOptions(options_list);
		
		//标签
		QuestionLabel questionLabel = questionLabelDao.getOne(labelId);
		questionSimple.setLabel(questionLabel);
		questionSimpleDao.saveAndFlush(questionSimple);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(String id, String shortName, String content, Integer difficulty, String options, String labelId, 
			String answerContent, String analyse) {
		QuestionSimple questionSimple = questionSimpleDao.getOne(id);
		if(questionSimple == null) {
			return new Result(false, "题目对象不存在");
		}
		if(shortName != "" && shortName != null) {
			questionSimple.setShortName(shortName);
		}
		if(content != null && content != "") {
			questionSimple.setContent(content);
		}
		if (difficulty != null) {
			questionSimple.setDifficulty(difficulty);
		}
		if(options != null && options != "") {
			questionSimple.getSimpleOptions().clear();
			List<Option> options_list = new ArrayList<>();
			String[] option_array = options.split(",");
			for (int i = 0; i < option_array.length; i++) {
				if(option_array[i] == "") {continue;}
				Option option = new Option(option_array[i]);
				options_list.add(option);
			}
			questionSimple.setSimpleOptions(options_list);
		}
		if(labelId != null && labelId != "") {
			QuestionLabel questionLabel = questionLabelDao.getOne(labelId);
			questionSimple.setLabel(questionLabel);
		}
		if(answerContent != null && answerContent != "") {
			questionSimple.getSimpleAnswer().setContent(answerContent);
		}
		if(analyse != null && analyse != "") {
			questionSimple.getSimpleAnswer().setAnalyse(analyse);
		}
		questionSimpleDao.saveAndFlush(questionSimple);
		return new Result(true, "更新成功");
	}

	@Transactional
	@Override
	public Result deleteById(String id) {
		questionSimpleDao.deleteById(id);
		return new Result(true, "删除成功");
	}

	@Override
	public QuestionSimple findById(String id) {
		return questionSimpleDao.getOne(id);
	}

	@Override
	public Map<String, Object> list(int pageNumber, int pageSize, String sortOrder) {
		Sort sort = new Sort(Direction.DESC, "createTime");
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber, pageSize, sort);
		List<QuestionSimple> list = questionSimpleDao.findAll(pageable).getContent();
		
		List<QuestionSimpleJson> questionSimpleJsons = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		for (QuestionSimple questionSimple : list) {
			QuestionSimpleJson tmp = new QuestionSimpleJson();
			tmp.setContent(questionSimple.getContent());
			tmp.setCreateTime(questionSimple.getCreateTime());
			tmp.setDifficulty(questionSimple.getDifficulty());
			tmp.setId(questionSimple.getId());
			tmp.setAuthorId(questionSimple.getAuthor().getUid());
			tmp.setAuthorName(questionSimple.getAuthor().getName());
			tmp.setLabelId(questionSimple.getLabel().getId());
			tmp.setLabelName(questionSimple.getLabel().getName());
			questionSimpleJsons.add(tmp);
		}
		map.put("total", questionSimpleJsons.size());
		map.put("rows", questionSimpleJsons);
		return map;
	}

	@Override
	public Result judgeResult(String qid, String solution) {
		return null;
	}

}
