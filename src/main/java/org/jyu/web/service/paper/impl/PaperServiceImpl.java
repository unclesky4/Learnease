package org.jyu.web.service.paper.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jyu.web.dao.authority.UserRepository;
import org.jyu.web.dao.paper.PaperLabelRepository;
import org.jyu.web.dao.paper.PaperRepository;
import org.jyu.web.dao.question.QuestionRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.entity.authority.User;
import org.jyu.web.entity.paper.Paper;
import org.jyu.web.entity.paper.PaperLabel;
import org.jyu.web.entity.question.Question;
import org.jyu.web.service.paper.PaperService;
import org.jyu.web.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaperServiceImpl implements PaperService {
	
	@Autowired
	private PaperRepository paperDao;
	
	@Autowired
	private PaperLabelRepository paperLabelDao;
	
	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private QuestionRepository questionDao;

	@Transactional
	@Override
	public Result save(String name, Integer status, String userId, String questionIds, Map<String, Integer> questionScore,
			String paperLabelIds) {
		Paper paper = new Paper();
		//提交者
		User user = userDao.getOne(userId);
		if (user == null) {
			return new Result(false, "请登陆");
		}
		paper.setAuthor(user);
		//处理试卷标签
		List<PaperLabel> list = new ArrayList<>();
		String[] labelId = paperLabelIds.split(",");
		int size = labelId.length;
		for (int i = 0; i < size; i++) {
			PaperLabel tmp = paperLabelDao.getOne(labelId[i]);
			if (tmp != null) {
				list.add(tmp);
			}
		}
		paper.setPaperLabels(list);
		
		paper.setStatus(status);
		paper.setName(name);
		paper.setQuestionScore(questionScore);
		
		//题目
		List<Question> questionset = new ArrayList<>();
		String[] questionId = questionIds.split(",");
		size = questionId.length;
		for (int i = 0; i < size; i++) {
			Question question = questionDao.getOne(questionId[i]);
			if (question != null) {
				questionset.add(question);
			}
		}
		paper.setQuestionset(questionset);
		
		paper.setCreateDate(DateUtil.DateToString(DateUtil.YMDHMS, new Date()));
		paperDao.saveAndFlush(paper);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(String id, String name, Integer status, String questionIds, Map<String, Integer> questionScore, String paperLabelIds) {
		Paper paper = paperDao.getOne(id);
		if (paper == null) {
			return new Result(false, "对象不存在");
		}
		if (name != null && name != "") {
			paper.setName(name);
		}
		if (status != null) {
			paper.setStatus(status);
		}
		if (questionIds != null && questionIds != "") {
			paper.getQuestionset().clear();
			
			String[] questionId = questionIds.split(",");
			int size = questionId.length;
			for (int i = 0; i < size; i++) {
				Question question = questionDao.getOne(questionId[i]);
				if (question != null) {
					paper.getQuestionset().add(question);
				}
			}
			paper.getQuestionScore().clear();
			paper.getQuestionScore().putAll(questionScore);
		}
		if (paperLabelIds != null && paperLabelIds != "") {
			paper.getPaperLabels().clear();
			String[] labelId = paperLabelIds.split(",");
			int size = labelId.length;
			for (int i = 0; i < size; i++) {
				PaperLabel tmp = paperLabelDao.getOne(labelId[i]);
				if (tmp != null) {
					paper.getPaperLabels().add(tmp);
				}
			}
		}
		paperDao.saveAndFlush(paper);
		return new Result(true, "修改成功");
	}

	@Transactional
	@Override
	public Result deleteById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> pageJson(Integer pageNumber, Integer pageSize, String sortOrder, String searchText) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		if (sortOrder.toLowerCase().equals("asc")) {
			sort = new Sort(Direction.ASC, "createDate");
		}
		
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber-1, pageSize, sort);
		
		Specification<Paper> specification = new Specification<Paper>() {

			private static final long serialVersionUID = -2853206393322356255L;

			@Override
			public Predicate toPredicate(Root<Paper> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (searchText != null && searchText != "") {
					Predicate p1 = cb.like(root.get("name"), "%"+searchText+"%");
					predicate = cb.and(predicate, p1);
				}
				return predicate;
			}
		};
		
		Page<Paper> page = paperDao.findAll(specification, pageable);
		
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		return map;
	}

}
