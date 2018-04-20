package org.jyu.web.controller.question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionBlankJson;
import org.jyu.web.entity.question.QuestionBlank;
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
	
	@RequestMapping(value="blank_add_student", method=RequestMethod.GET)
	public ModelAndView blank_add_student(ModelAndView mv) {
		mv.setViewName("/question/student/blank_add.html");
		return mv;
	}
	
	@RequestMapping(value="blank_up_student", method=RequestMethod.GET)
	public ModelAndView blank_up_student(ModelAndView mv) {
		mv.setViewName("/question/student/blank_up.html");
		return mv;
	}
	
	@RequestMapping(value="blank_query_student", method=RequestMethod.GET)
	public ModelAndView blank_query_student(ModelAndView mv) {
		mv.setViewName("/question/student/blank_query.html");
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
		
		if(questionBlank.getAuthor() != null) {
			questionBlankJson.setAuthorId(questionBlank.getAuthor().getUid());
			questionBlankJson.setAuthorName(questionBlank.getAuthor().getName());
		}
		return questionBlankJson;
	}

}
