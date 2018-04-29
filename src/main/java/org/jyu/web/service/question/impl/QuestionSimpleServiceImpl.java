package org.jyu.web.service.question.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.jyu.web.dao.authority.UserRepository;
import org.jyu.web.dao.question.QuestionLabelRepository;
import org.jyu.web.dao.question.QuestionSimpleRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionSimpleJson;
import org.jyu.web.entity.authority.User;
import org.jyu.web.entity.question.Answer;
import org.jyu.web.entity.question.Option;
import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.entity.question.QuestionSimple;
import org.jyu.web.enums.QuestionType;
import org.jyu.web.service.question.QuestionSimpleService;
import org.jyu.web.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class QuestionSimpleServiceImpl implements QuestionSimpleService {
	
	@Autowired
	private QuestionSimpleRepository questionSimpleDao;
	
	@Autowired
	private QuestionLabelRepository questionLabelDao;
	
	@Autowired
	private UserRepository userDao;

	@Transactional
	@Override
	public Result save(String shortName, String content, Integer difficulty, List<String> options, List<String> labelIds,
			String answerContent, String analyse, String userId) {
		//单选题对象
		if (questionSimpleDao.findByShortName(shortName).size() > 0) {
			return new Result(false, "主题重复");
		}
		QuestionSimple questionSimple = new QuestionSimple();
		questionSimple.setShortName(shortName);
		questionSimple.setContent(content);
		questionSimple.setCreateTime(DateUtil.DateToString(DateUtil.YMDHMS, new Date()));
		questionSimple.setDifficulty(difficulty);
		User user = userDao.getOne(userId);
		if(user != null && user.getTeacher() != null) {
			questionSimple.setAuthor(user);
		}
		questionSimple.setType(QuestionType.SIMPLE);
		//答案
		Answer answer = new Answer(analyse, answerContent);
		questionSimple.setSimpleAnswer(answer);
		
		//选项
		List<Option> option_list = new ArrayList<>();
		for (String name : options) {
			Option option = new Option(name);
			option_list.add(option);
		}
		questionSimple.setSimpleOptions(option_list);
		
		//标签
		List<QuestionLabel> labels = new ArrayList<>();
		for (String labelId : labelIds) {
			QuestionLabel questionLabel = questionLabelDao.getOne(labelId);
			if (questionLabel != null) {
				labels.add(questionLabel);
			}
		}
		questionSimple.setQuestionLabels(labels);
		questionSimpleDao.saveAndFlush(questionSimple);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(String id, String shortName, String content, Integer difficulty, List<String> options, List<String> labelIds, 
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
		if(options != null && options.size() > 0) {
			questionSimple.getSimpleOptions().clear();
			List<Option> option_list = new ArrayList<>();
			for (String name : options) {
				Option option = new Option(name);
				option_list.add(option);
			}
			questionSimple.setSimpleOptions(option_list);
		}
		if(labelIds != null && labelIds.size() > 0) {
			questionSimple.getQuestionLabels().clear();
			List<QuestionLabel> labels = new ArrayList<>();
			for (String labelId : labelIds) {
				QuestionLabel questionLabel = questionLabelDao.getOne(labelId);
				if (questionLabel != null) {
					labels.add(questionLabel);
				}
			}
			questionSimple.setQuestionLabels(labels);
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
		Pageable pageable = new PageRequest(pageNumber-1, pageSize, sort);
		Page<QuestionSimple> page = questionSimpleDao.findAll(pageable);
		List<QuestionSimple> list = page.getContent();
		
		Map<String, Object> map = new HashMap<>();
		List<QuestionSimpleJson> questionSimpleJsons = convertData(list);
		map.put("total", page.getTotalElements());
		map.put("rows", questionSimpleJsons);
		return map;
	}

	@Override
	public Result judgeResult(String qid, String solution) {
		return null;
	}

	@Override
	public Map<String, Object> getPageByUser(int pageNumber, int pageSize, String sortOrder, String userId) {
		Sort sort = new Sort(Direction.DESC, "createTime");
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber-1, pageSize, sort);
		
		Specification<QuestionSimple> specification = new Specification<QuestionSimple>() {

			private static final long serialVersionUID = 3011558012551930039L;

			@Override
			public Predicate toPredicate(Root<QuestionSimple> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.equal(root.get("author").get("uid"), userId);
				return predicate;
			}
		};
		
		Page<QuestionSimple> page = questionSimpleDao.findAll(specification, pageable);
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", convertData(page.getContent()));
		return map;
	}
	
	/**
	 * 对象转换
	 * @param questionSimples
	 * @return
	 */
	List<QuestionSimpleJson> convertData(List<QuestionSimple> questionSimples) {
		List<QuestionSimpleJson> questionSimpleJsons = new ArrayList<>();
		if (questionSimples == null) {
			return questionSimpleJsons;
		}
		for (QuestionSimple questionSimple : questionSimples) {
			QuestionSimpleJson tmp = new QuestionSimpleJson();
			tmp.setContent(questionSimple.getContent());
			tmp.setCreateTime(questionSimple.getCreateTime());
			tmp.setDifficulty(questionSimple.getDifficulty());
			tmp.setShortName(questionSimple.getShortName());
			tmp.setId(questionSimple.getId());
			if (questionSimple.getAuthor() != null) {
				tmp.setAuthorId(questionSimple.getAuthor().getUid());
				tmp.setAuthorName(questionSimple.getAuthor().getName());
			}
			List<QuestionLabel> labels = questionSimple.getQuestionLabels();
			String[] labels_array = new String[labels.size()];
			for (int i = 0; i < labels.size(); i++) {
				labels_array[i] = labels.get(i).getName();
			}
			tmp.setLabels(Arrays.toString(labels_array));
			
			List<Option> options = questionSimple.getSimpleOptions();
			String[] options_array = new String[options.size()];
			for (int i = 0; i < options.size(); i++) {
				options_array[i] = options.get(i).getContent();
			}
			tmp.setOptions(Arrays.toString(options_array));
			questionSimpleJsons.add(tmp);
		}
		return questionSimpleJsons;
	}

}
