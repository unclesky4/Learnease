package org.jyu.web.service.question.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jyu.web.dao.authority.UserRepository;
import org.jyu.web.dao.question.QuestionLabelRepository;
import org.jyu.web.dao.question.QuestionMultipleRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionMultipleJson;
import org.jyu.web.entity.question.Answer;
import org.jyu.web.entity.question.Option;
import org.jyu.web.entity.question.QuestionMultiple;
import org.jyu.web.service.question.QuestionMultipleService;
import org.jyu.web.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionMultipleServiceImpl implements QuestionMultipleService {
	
	@Autowired
	private QuestionMultipleRepository questionMultipleDao;
	
	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private QuestionLabelRepository questionLabelDao;

	@Transactional
	@Override
	public Result save(String shortName, String content, Integer difficulty, String labelId, String answerContent,
			String analyse, String options, String userId) {
		QuestionMultiple multiple = new QuestionMultiple();
		multiple.setShortName(shortName);
		multiple.setContent(content);
		multiple.setDifficulty(difficulty);
		multiple.setAuthor(userDao.getOne(userId));
		
		//选项
		List<Option> options_list = new ArrayList<>();
		String[] option = options.split(",");
		for (int i = 0; i < option.length; i++) {
			if (option[i] == "") {
				continue;
			}
			Option tmp = new Option();
			tmp.setContent(option[i]);
			options_list.add(tmp);
		}
		multiple.setOptions(options_list);
		
		//参考答案
		Answer answer = new Answer(analyse, answerContent);
		multiple.setAnswer(answer);
		
		//标签
		multiple.setLabel(questionLabelDao.getOne(labelId));
		
		questionMultipleDao.saveAndFlush(multiple);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(String id, String shortName, String content, Integer difficulty, String labelId,
			String answerContent, String analyse, String options) {
		QuestionMultiple multiple = questionMultipleDao.getOne(id);
		if (multiple == null) {
			return new Result(false, "对象不存在");
		}
		if(shortName != null && shortName != "") {
			multiple.setShortName(shortName);
		}
		if(content != null && content != "") {
			multiple.setContent(content);
		}
		if(difficulty != null) {
			multiple.setDifficulty(difficulty);
		}
		if(labelId != null && labelId != "") {
			multiple.setLabel(questionLabelDao.getOne(labelId));
		}
		if(answerContent != null && answerContent != "") {
			multiple.getAnswer().setContent(answerContent);
		}
		if(analyse != null && analyse != "") {
			multiple.getAnswer().setAnalyse(analyse);
		}
		if(options != null && options != "") {
			List<Option> options_list = new ArrayList<>();
			String[] option = options.split(",");
			for (int i = 0; i < option.length; i++) {
				if (option[i] == "") {
					continue;
				}
				Option tmp = new Option();
				tmp.setContent(option[i]);
				options_list.add(tmp);
			}
			multiple.setOptions(options_list);
		}
		questionMultipleDao.saveAndFlush(multiple);
		return new Result(true, "修改成功");
	}

	@Transactional
	@Override
	public Result delete(String id) {
		questionMultipleDao.deleteById(id);
		return new Result(true, "删除成功");
	}

	@Override
	public QuestionMultiple findById(String id) {
		QuestionMultiple multiple = questionMultipleDao.getOne(id);
		return multiple;
	}
	
	@Override
	public Map<String, Object> list(int pageNumber, int pageSize, String sortOrder) {
		Sort sort = new Sort(Direction.DESC,"createTime");
		if(sortOrder.toLowerCase().equals("asc")) {
			sort = new Sort(Direction.ASC,"createTime");
		}
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber-1, pageSize,sort);
		Page<QuestionMultiple> page = questionMultipleDao.findAll(pageable);
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", this.convertData(page.getContent()));
		return map;
	}

	List<QuestionMultipleJson> convertData(List<QuestionMultiple> multiples) {
		List<QuestionMultipleJson> list = new ArrayList<>();
		for (QuestionMultiple questionMultiple : multiples) {
			QuestionMultipleJson json = new QuestionMultipleJson();
			json.setAuthorId(questionMultiple.getAuthor().getUid());
			json.setAuthorName(questionMultiple.getAuthor().getName());
			json.setContent(questionMultiple.getContent());
			json.setCreateTime(questionMultiple.getCreateTime());
			json.setDifficulty(questionMultiple.getDifficulty());
			json.setId(questionMultiple.getId());
			json.setOptions(questionMultiple.getOptions());
			json.setType(questionMultiple.getType());
			list.add(json);
		}
		System.err.println(JsonUtil.toJson(list));
		return list;
	}
}
