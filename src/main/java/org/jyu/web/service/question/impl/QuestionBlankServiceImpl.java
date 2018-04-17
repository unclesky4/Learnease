package org.jyu.web.service.question.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jyu.web.dao.authority.UserRepository;
import org.jyu.web.dao.question.QuestionBlankRepository;
import org.jyu.web.dao.question.QuestionLabelRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionBlankJson;
import org.jyu.web.entity.authority.User;
import org.jyu.web.entity.question.Answer;
import org.jyu.web.entity.question.QuestionBlank;
import org.jyu.web.enums.QuestionType;
import org.jyu.web.service.question.QuestionBlankService;
import org.jyu.web.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
	public Result save(String shortName, String content, Integer difficulty, String userId, String labelId, String answerContent, 
			String analyse) {
		QuestionBlank questionBlank = new QuestionBlank();
		User user = userDao.getOne(userId);
		questionBlank.setAuthor(user);
		questionBlank.setShortName(shortName);
		questionBlank.setContent(content);
		questionBlank.setDifficulty(difficulty);
		questionBlank.setCreateTime(DateUtil.DateToString(DateUtil.YMDHMS, new Date()));
		questionBlank.setType(QuestionType.BLANK);
		
		questionBlank.setLabel(questionLabelDao.getOne(labelId));
		
		Answer answer = new Answer();
		answer.setAnalyse(analyse);
		answer.setContent(answerContent);
		questionBlank.setAnswer(answer);
		questionBlankDao.saveAndFlush(questionBlank);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(String id, String shortName, String content, Integer difficulty, String labelId, String answerContent, 
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
		if(labelId != null && labelId != "") {
			questionBlank.setLabel(questionLabelDao.getOne(labelId));
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
	
	List<QuestionBlankJson> convertData(List<QuestionBlank> questionBlanks) {
		List<QuestionBlankJson> list = new ArrayList<>();
		for (QuestionBlank questionBlank : questionBlanks) {
			QuestionBlankJson questionBlankJson = new QuestionBlankJson();
			questionBlankJson.setId(questionBlank.getId());
			questionBlankJson.setContent(questionBlank.getContent());
			questionBlankJson.setCreateTime(questionBlank.getCreateTime());
			questionBlankJson.setDifficulty(questionBlank.getDifficulty());
			questionBlankJson.setType(questionBlank.getType());
			if(questionBlank.getAuthor() != null) {
				questionBlankJson.setAuthorId(questionBlank.getAuthor().getUid());
				questionBlankJson.setAuthorName(questionBlank.getAuthor().getName());
			}
			list.add(questionBlankJson);
		}
		return list;
	}

	@Override
	public Result judgeResult(String qid, String solution) {
		// TODO Auto-generated method stub
		return null;
	}

}
