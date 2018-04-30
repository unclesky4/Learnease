package org.jyu.web.controller.question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.AnswerJson;
import org.jyu.web.dto.question.QuestionBlankJson;
import org.jyu.web.entity.question.Answer;
import org.jyu.web.entity.question.QuestionBlank;
import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.service.question.QuestionBlankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class QuestionBlankController {
	
	@Autowired
	private QuestionBlankService questionBlankService;
	
	@RequestMapping(value="blank_list_student", method=RequestMethod.GET)
	public ModelAndView blank_list_student(ModelAndView mv) {
		mv.setViewName("/question/student/blank_list.html");
		return mv;
	}
	
	/**
	 * 修改填空题界面
	 * @param mv
	 * @param id   判断题主键
	 * @return
	 */
	@RequestMapping(value="blank_up_html", method=RequestMethod.GET)
	public ModelAndView blank_up_html(ModelAndView mv, String id) {
		mv.setViewName("/question/teacher/blank_up.html");
		return mv;
	}
	
	/**
	 * 保存填空题
	 * @param shortName 问题简述
	 * @param content   问题主干
	 * @param difficulty  难度
	 * @param labelIds  标签（用逗号隔开）
	 * @param answerContent  参考答案内容
	 * @param analyse  参考答案分析
	 */
	@RequestMapping(value="/blank/save",method=RequestMethod.POST)
	public Result save(String shortName, String content, Integer difficulty, String labelIds, String answerContent, 
			String analyse) {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		List<String> label_list = new ArrayList<>();
		String[] label_array = labelIds.split(",");
		for (int i = 0; i < label_array.length; i++) {
			if (label_array[i].equals("")) {
				continue;
			}
			label_list.add(label_array[i]);
		}
		return questionBlankService.save(shortName, content, difficulty, userId, label_list, answerContent, analyse);
	}
	
	/**
	 * 删除填空题
	 * @param id  主键
	 */
	@RequestMapping(value="/blank/delete",method=RequestMethod.POST)
	public Result deleteById(String id) {
		return questionBlankService.deleteById(id);
	}
	
	/**
	 * 修改填空题
	 * @param id
	 * @param shortName 问题简述
	 * @param content   问题主干
	 * @param difficulty  难度
	 * @param labelIds  标签（用逗号隔开）
	 * @param answerContent  参考答案内容
	 * @param analyse  参考答案分析
	 */
	@RequestMapping(value="/blank/update",method=RequestMethod.POST)
	public Result update(String id, String shortName, String content, Integer difficulty, String labelIds, String answerContent, 
			String analyse) {
		List<String> label_list = new ArrayList<>();
		String[] label_array = labelIds.split(",");
		for (int i = 0; i < label_array.length; i++) {
			if (label_array[i].equals("")) {
				continue;
			}
			label_list.add(label_array[i]);
		}
		return questionBlankService.update(id, shortName, content, difficulty, label_list, answerContent, analyse);
	}
	
	/**
	 * 通过主键查询填空题
	 * @param id
	 */
	@RequestMapping(value="/blank/getById",method=RequestMethod.GET)
	public QuestionBlankJson getById(String id) {
		return convert(questionBlankService.findById(id));
	}
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @return
	 */
	@RequestMapping(value="/blank/all",method=RequestMethod.GET)
	public Map<String, Object> getPageJson(int pageNumber, int pageSize, String sortOrder) {
		return questionBlankService.list(pageNumber, pageSize, sortOrder);
	}
	
	@RequestMapping(value="/blank/judge",method=RequestMethod.POST)
	public Result judgeAnswer(String qid, String answercontent) {
		return null;
	}
	
	/**
	 * 分页查询登陆用户提交的编程题
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @return
	 */
	@RequestMapping(value="/blank/own", method=RequestMethod.GET)
	public Map<String, Object> getOwnQuestionProgram(int pageNumber, int pageSize, String sortOrder) {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		
		Map<String, Object> map = new HashMap<>();
		if (userId == null) {
			return map;
		}
		return questionBlankService.getPageByUser(pageNumber, pageSize, sortOrder, userId);
	}
	
	/**
	 * 获取某个填空题的参考答案
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/blank/answer", method=RequestMethod.GET)
	public AnswerJson getAnswer(String id) {
		Answer answer = questionBlankService.findById(id).getAnswer();
		AnswerJson json = new AnswerJson();
		json.setId(answer.getId());
		json.setAnalyse(answer.getAnalyse());
		json.setContent(answer.getContent());
		return json;
	}
	
	
	QuestionBlankJson convert(QuestionBlank questionBlank) {
		QuestionBlankJson questionBlankJson = new QuestionBlankJson();
		if(questionBlank == null) {
			return null;
		}
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
		return questionBlankJson;
	}

}
