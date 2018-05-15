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

import org.jyu.web.dao.authority.UserRepository;
import org.jyu.web.dao.manage.QuestionLabelRepository;
import org.jyu.web.dao.question.QuestionJudgementRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionJudgementJson;
import org.jyu.web.entity.authority.User;
import org.jyu.web.entity.question.Answer;
import org.jyu.web.entity.question.Option;
import org.jyu.web.entity.question.QuestionJudgement;
import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.enums.QuestionType;
import org.jyu.web.service.question.QuestionJudgementService;
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
public class QuestionJudgementServiceImpl implements QuestionJudgementService {
	
	@Autowired
	private QuestionJudgementRepository questionJudgementDao;
	
	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private QuestionLabelRepository questionLabelDao;

	@Transactional
	@Override
	public Result save(String shortName, String content, Integer difficulty, String userId, List<String> labelIds, String answerContent,
			String analyse) {
		if (questionJudgementDao.findByShortName(shortName).size() > 0) {
			return new Result(false, "主题重复");
		}
		QuestionJudgement questionJudgement = new QuestionJudgement();
		User user = userDao.getOne(userId);
		questionJudgement.setShortName(shortName);
		questionJudgement.setAuthor(user);
		questionJudgement.setContent(content);
		questionJudgement.setDifficulty(difficulty);
		questionJudgement.setCreateTime(DateUtil.DateToString(DateUtil.YMDHMS, new Date()));
		questionJudgement.setType(QuestionType.JUDGEMENT);
		
		//标签
		List<QuestionLabel> labels = new ArrayList<>();
		for (String labelId : labelIds) {
			QuestionLabel questionLabel = questionLabelDao.getOne(labelId);
			if (questionLabel != null) {
				labels.add(questionLabel);
			}
		}
		questionJudgement.setQuestionLabels(labels);
		
		Option option1 = new Option("对");
		Option option2 = new Option("错");
		List<Option> options = new ArrayList<>();
		options.add(option1);
		options.add(option2);
		questionJudgement.setJudgementOptions(options);
		
		Answer answer = new Answer();
		answer.setAnalyse(analyse);
		answer.setContent(answerContent);
		questionJudgement.setAnswer(answer);
		questionJudgementDao.saveAndFlush(questionJudgement);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(String id, String shortName, String content, Integer difficulty, List<String> labelIds, String answerContent,
			String analyse) {
		QuestionJudgement judgement = questionJudgementDao.getOne(id);
		if(judgement == null) {
			return new Result(false, "修改失败");
		}
		if(shortName != null && shortName != "") {
			judgement.setShortName(shortName);
		}
		if(content != null && content != "") {
			judgement.setContent(content);
		}
		if(difficulty != null) {
			judgement.setDifficulty(difficulty);
		}
		if (labelIds != null && labelIds.size() > 0) {
			judgement.getQuestionLabels().clear();
			List<QuestionLabel> labels = new ArrayList<>();
			for (String labelId : labelIds) {
				QuestionLabel questionLabel = questionLabelDao.getOne(labelId);
				if (questionLabel != null) {
					labels.add(questionLabel);
				}
			}
			judgement.setQuestionLabels(labels);
		}
		if (answerContent != null && answerContent != "") {
			judgement.getAnswer().setContent(answerContent);
		}
		if (analyse != null && analyse != "") {
			judgement.getAnswer().setAnalyse(analyse);
		}
		questionJudgementDao.saveAndFlush(judgement);
		return new Result(true, "修改成功");
	}

	@Transactional
	@Override
	public Result deleteById(String id) {
		questionJudgementDao.deleteById(id);
		return new Result(true, "删除成功");
	}

	@Override
	public QuestionJudgement findById(String id) {
		return questionJudgementDao.getOne(id);
	}

	@Override
	public Map<String, Object> list(int pageNumber, int pageSize, String sortOrder) {
		Sort sort = new Sort(Direction.DESC,"createTime");
		if(sortOrder.toLowerCase().equals("asc")) {
			sort = new Sort(Direction.ASC,"createTime");
		}
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber-1, pageSize,sort);
		
		Page<QuestionJudgement> page = questionJudgementDao.findAll(pageable);
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", this.convertData(page.getContent()));
		return map;
	}

	@Override
	public Result judgeResult(String qid, String solution) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> getPageByUser(int pageNumber, int pageSize, String sortOrder, String userId) {
		Sort sort = new Sort(Direction.DESC, "createTime");
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber-1, pageSize, sort);
		
		Specification<QuestionJudgement> specification = new Specification<QuestionJudgement>() {

			private static final long serialVersionUID = 3011558012551930039L;

			@Override
			public Predicate toPredicate(Root<QuestionJudgement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.equal(root.get("author").get("uid"), userId);
				return predicate;
			}
		};
		
		Page<QuestionJudgement> page = questionJudgementDao.findAll(specification, pageable);
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", convertData(page.getContent()));
		return map;
	}
	
	List<QuestionJudgementJson> convertData(List<QuestionJudgement> judgements) {
		List<QuestionJudgementJson> list = new ArrayList<>();
		for (QuestionJudgement questionJudgement : judgements) {
			QuestionJudgementJson json = new QuestionJudgementJson();
			json.setId(questionJudgement.getId());
			json.setContent(questionJudgement.getContent());
			json.setCreateTime(questionJudgement.getCreateTime());
			json.setDifficulty(questionJudgement.getDifficulty());
			json.setType(questionJudgement.getType());
			json.setShortName(questionJudgement.getShortName());
			
			List<QuestionLabel> labels = questionJudgement.getQuestionLabels();
			String[] labels_array = new String[labels.size()];
			for (int i = 0; i < labels.size(); i++) {
				labels_array[i] = labels.get(i).getName();
			}
			json.setLabels(Arrays.toString(labels_array));
			
			List<Option> options = questionJudgement.getJudgementOptions();
			String[] options_array = new String[options.size()];
			for (int i = 0; i < options.size(); i++) {
				options_array[i] = options.get(i).getContent();
			}
			json.setOptions(Arrays.toString(options_array));
			
			if(questionJudgement.getAuthor() != null) {
				json.setAuthorId(questionJudgement.getAuthor().getUid());
				json.setAuthorName(questionJudgement.getAuthor().getName());
			}
			list.add(json);
		}
		return list;
	}

}
