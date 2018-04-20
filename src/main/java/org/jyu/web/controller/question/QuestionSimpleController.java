package org.jyu.web.controller.question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionSimpleJson;
import org.jyu.web.entity.question.Option;
import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.entity.question.QuestionSimple;
import org.jyu.web.service.question.QuestionSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class QuestionSimpleController {
	
	@Autowired
	private QuestionSimpleService questionSimpleService;
	
	@RequestMapping(value="simple_list_student", method=RequestMethod.GET)
	public ModelAndView simple_list_student(ModelAndView mv) {
		mv.setViewName("/question/student/simple_list.html");
		return mv;
	}
	
	@RequestMapping(value="simple_up_html", method=RequestMethod.GET)
	public ModelAndView simple_up_html(ModelAndView mv) {
		mv.setViewName("/question/teacher/simple_up.html");
		return mv;
	}
	
	@RequestMapping(value="simple_info_student_html", method=RequestMethod.GET)
	public ModelAndView simple_info_student(ModelAndView mv) {
		mv.setViewName("/question/student/simple_info.html");
		return mv;
	}
	
	/**
	 * 保存单选题
	 * @param shortName
	 * @param content
	 * @param difficulty
	 * @param options
	 * @param labelIds
	 * @param answerContent
	 * @param analyse
	 * @return
	 */
	@RequestMapping(value="/simple/save", method=RequestMethod.POST)
	public Result save(String shortName, String content, Integer difficulty, String options, String labelIds, 
			String answerContent, String analyse) {
		List<String> option_list = new ArrayList<>();
		String[] option_array = options.split(",");
		for (int i = 0; i < option_array.length; i++) {
			if (option_array[i].equals("")) {
				continue;
			}
			option_list.add(option_array[i]);
		}
		List<String> label_list = new ArrayList<>();
		String[] label_array = labelIds.split(",");
		for (int i = 0; i < label_array.length; i++) {
			if (label_array[i].equals("")) {
				continue;
			}
			label_list.add(label_array[i]);
		}
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		if (userId == null) {
			new Result(false, "请登陆");
		}
		return questionSimpleService.save(shortName, content, difficulty, option_list, label_list, answerContent, analyse, userId);
	}
	
	/**
	 * 更新单选题信息
	 * @param id
	 * @param shortName
	 * @param content
	 * @param difficulty
	 * @param options
	 * @param labelId
	 * @param answerContent
	 * @param analyse
	 * @return
	 */
	@RequestMapping(value="/simple/update", method=RequestMethod.POST)
	public Result update(String id, String shortName, String content, Integer difficulty, String options, String labelIds, 
			String answerContent, String analyse) {
		List<String> option_list = new ArrayList<>();
		if (options != null) {
			String[] option_array = options.split(",");
			for (int i = 0; i < option_array.length; i++) {
				if (option_array[i].equals("")) {
					continue;
				}
				option_list.add(option_array[i]);
			}
		}
		List<String> label_list = new ArrayList<>();
		if (labelIds != null) {
			String[] label_array = labelIds.split(",");
			for (int i = 0; i < label_array.length; i++) {
				if (label_array[i].equals("")) {
					continue;
				}
				label_list.add(label_array[i]);
			}
		}
		return questionSimpleService.update(id, shortName, content, difficulty, option_list, label_list, answerContent, analyse);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/simple/delete", method=RequestMethod.POST)
	public Result delete(String id) {
		return questionSimpleService.deleteById(id);
	}
	
	/**
	 * 通过主键查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/simple/simple/getById", method=RequestMethod.GET)
	public QuestionSimpleJson getById(String id) {
		return convert(questionSimpleService.findById(id));
	}
	
	/**
	 * 分页
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @return
	 */
	@RequestMapping(value="/simple/all", method=RequestMethod.GET)
	public Map<String, Object> getPageJson(int pageNumber, int pageSize, String sortOrder) {
		return questionSimpleService.list(pageNumber, pageSize, sortOrder);
	}
	
	/**
	 * 判断用户提交的答案
	 * @param qid
	 * @param answercontent
	 * @return
	 */
	@RequestMapping(value="/simple/judge",method=RequestMethod.POST)
	public Result judgeAnswer(String qid, String answercontent) {
		return null;
	}
	
	
	QuestionSimpleJson convert(QuestionSimple questionSimple) {
		if (questionSimple == null) {
			return null;
		}
		QuestionSimpleJson json = new QuestionSimpleJson();
		json.setContent(questionSimple.getContent());
		json.setCreateTime(questionSimple.getCreateTime());
		json.setDifficulty(questionSimple.getDifficulty());
		json.setShortName(questionSimple.getShortName());
		json.setId(questionSimple.getId());
		List<String> labels = new ArrayList<>();
		for (QuestionLabel questionLabel : questionSimple.getQuestionLabels()) {
			labels.add(questionLabel.getName());
		}
		json.setLabelName(labels.toString().replaceAll("[", "").replaceAll("]", ""));
		List<String> options = new ArrayList<>();
		for (Option option : questionSimple.getSimpleOptions()) {
			options.add(option.getContent());
		}
		json.setOptions(options.toString().replaceAll("[", "").replaceAll("]", ""));
		json.setAuthorId(questionSimple.getAuthor().getUid());
		json.setAuthorName(questionSimple.getAuthor().getName());
		return json;
	}

}
