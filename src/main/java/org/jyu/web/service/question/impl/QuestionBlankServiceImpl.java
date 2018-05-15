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
import org.jyu.web.dao.question.QuestionBlankRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionBlankJson;
import org.jyu.web.entity.authority.User;
import org.jyu.web.entity.question.Answer;
import org.jyu.web.entity.question.QuestionBlank;
import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.enums.QuestionType;
import org.jyu.web.service.question.QuestionBlankService;
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
public class QuestionBlankServiceImpl implements QuestionBlankService {
	
	
	@Autowired
	private QuestionBlankRepository questionBlankDao;
	
	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private QuestionLabelRepository questionLabelDao;

	@Transactional
	@Override
	public Result save(String shortName, String content, Integer difficulty, String userId, List<String> labelIds, String answerContent, 
			String analyse) {
		if (questionBlankDao.findByShortName(shortName).size() > 0) {
			return new Result(false, "主题重复");
		}
		QuestionBlank questionBlank = new QuestionBlank();
		User user = userDao.getOne(userId);
		questionBlank.setAuthor(user);
		questionBlank.setShortName(shortName);
		questionBlank.setContent(content);
		questionBlank.setDifficulty(difficulty);
		questionBlank.setCreateTime(DateUtil.DateToString(DateUtil.YMDHMS, new Date()));
		questionBlank.setType(QuestionType.BLANK);
		
		//标签
		List<QuestionLabel> labels = new ArrayList<>();
		for (String labelId : labelIds) {
			QuestionLabel questionLabel = questionLabelDao.getOne(labelId);
			if (questionLabel != null) {
				labels.add(questionLabel);
			}
		}
		questionBlank.setQuestionLabels(labels);
		
		Answer answer = new Answer();
		answer.setAnalyse(analyse);
		answer.setContent(answerContent);
		questionBlank.setAnswer(answer);
		questionBlankDao.saveAndFlush(questionBlank);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(String id, String shortName, String content, Integer difficulty, List<String> labelIds, String answerContent, 
			String analyse) {
		QuestionBlank questionBlank = questionBlankDao.getOne(id);
		if(questionBlank == null) {
			return new Result(false, "题目不存在");
		}
		if(shortName != "" && shortName != null) {
			questionBlank.setShortName(shortName);
		}
		if(content != null && content != "") {
			questionBlank.setContent(content);
		}
		if(difficulty != null) {
			questionBlank.setDifficulty(difficulty);
		}
		if(labelIds != null && labelIds.size() > 0) {
			questionBlank.getQuestionLabels().clear();
			List<QuestionLabel> labels = new ArrayList<>();
			for (String labelId : labelIds) {
				QuestionLabel questionLabel = questionLabelDao.getOne(labelId);
				if (questionLabel != null) {
					labels.add(questionLabel);
				}
			}
			questionBlank.setQuestionLabels(labels);
		}
		if(analyse != "" && analyse != null && answerContent != "" & answerContent != null){
			Answer answer = new Answer();
			answer.setAnalyse(analyse);
			answer.setContent(answerContent);
			questionBlank.setAnswer(answer);
		}
		questionBlankDao.saveAndFlush(questionBlank);
		return new Result(true, "修改成功");
	}

	@Transactional
	@Override
	public Result deleteById(String id) {
		questionBlankDao.deleteById(id);
		return new Result(true, "删除成功");
	}

	@Override
	public QuestionBlank findById(String id) {
		QuestionBlank questionBlank = questionBlankDao.getOne(id);
		return questionBlank;
	}

	@Override
	public Map<String, Object> list(int pageNumber, int pageSize, String sortOrder) {
		Sort sort = new Sort(Direction.DESC,"createTime");
		if(sortOrder.toLowerCase().equals("asc")) {
			sort = new Sort(Direction.ASC,"createTime");
		}
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber-1, pageSize,sort);
		
		Page<QuestionBlank> page = questionBlankDao.findAll(pageable);
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
		
		Specification<QuestionBlank> specification = new Specification<QuestionBlank>() {

			private static final long serialVersionUID = 3011558012551930039L;

			@Override
			public Predicate toPredicate(Root<QuestionBlank> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.equal(root.get("author").get("uid"), userId);
				return predicate;
			}
		};
		
		Page<QuestionBlank> page = questionBlankDao.findAll(specification, pageable);
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", convertData(page.getContent()));
		return map;
	}
	
	List<QuestionBlankJson> convertData(List<QuestionBlank> questionBlanks) {
		List<QuestionBlankJson> list = new ArrayList<>();
		for (QuestionBlank questionBlank : questionBlanks) {
			QuestionBlankJson questionBlankJson = new QuestionBlankJson();
			questionBlankJson.setId(questionBlank.getId());
			questionBlankJson.setContent(questionBlank.getContent());
			questionBlankJson.setCreateTime(questionBlank.getCreateTime());
			questionBlankJson.setDifficulty(questionBlank.getDifficulty());
			questionBlankJson.setType(questionBlank.getType());
			questionBlankJson.setShortName(questionBlank.getShortName());
			
			List<QuestionLabel> labels = questionBlank.getQuestionLabels();
			String[] labels_array = new String[labels.size()];
			for (int i = 0; i < labels.size(); i++) {
				labels_array[i] = labels.get(i).getName();
			}
			questionBlankJson.setLabels(Arrays.toString(labels_array));
			
			if(questionBlank.getAuthor() != null) {
				questionBlankJson.setAuthorId(questionBlank.getAuthor().getUid());
				questionBlankJson.setAuthorName(questionBlank.getAuthor().getName());
			}
			list.add(questionBlankJson);
		}
		return list;
	}

}
