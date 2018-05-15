package org.jyu.web.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.jyu.web.dao.manage.QuestionLabelRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.manage.LabelJson;
import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.service.manage.QuestionLabelService;
import org.jyu.web.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class QuestionLabelServiceImpl implements QuestionLabelService {
	
	@Autowired
	private QuestionLabelRepository questionLabelDao;

	@Transactional
	@Override
	public Result save(String name) {
		if (questionLabelDao.findByName(name) != null) {
			return new Result(false, "标签已存在");
		}
		QuestionLabel questionLabel = new QuestionLabel();
		questionLabel.setName(name);
		questionLabel.setCreateTime(DateUtil.DateToString(DateUtil.YMDHMS, new Date()));
		questionLabelDao.saveAndFlush(questionLabel);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(String id, String name) {
		QuestionLabel questionLabel = questionLabelDao.getOne(id);
		questionLabel.setName(name);
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
	public List<LabelJson> list() {
		return convertList(questionLabelDao.findAll());
	}

	@Override
	public Map<String, Object> pageJson(Integer pageNumber, Integer pageSize) {
		Map<String, Object> map = new HashMap<>();
		
		Sort sort = new Sort(Direction.DESC, "createTime");
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber -1, pageSize, sort);
		Page<QuestionLabel> page = questionLabelDao.findAll(pageable);
		
		map.put("total", page.getTotalElements());
		map.put("rows", convertList(page.getContent()));
		return map;
	}
	
	
	List<LabelJson> convertList(List<QuestionLabel> questionLabels) {
		List<LabelJson> list = new ArrayList<>();
		if (questionLabels == null) {
			return list;
		}
		for (QuestionLabel questionLabel : questionLabels) {
			LabelJson json = new LabelJson();
			json.setId(questionLabel.getId());
			json.setCreateTime(questionLabel.getCreateTime());
			json.setName(questionLabel.getName());
			list.add(json);
		}
		return list;
	}

}
