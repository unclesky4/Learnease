package org.jyu.web.controller.question;

import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionSimpleJson;
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
	
	@RequestMapping(value="simple_add_student", method=RequestMethod.GET)
	public ModelAndView simple_add_student(ModelAndView mv) {
		mv.setViewName("/question/student/simple_add.html");
		return mv;
	}
	
	@RequestMapping(value="simple_up_student", method=RequestMethod.GET)
	public ModelAndView simple_up_student(ModelAndView mv) {
		mv.setViewName("/question/student/simple_up.html");
		return mv;
	}
	
	@RequestMapping(value="simple_query_student", method=RequestMethod.GET)
	public ModelAndView simple_query_student(ModelAndView mv) {
		mv.setViewName("/question/student/simple_query.html");
		return mv;
	}
	
	/**
	 * 保存单选题
	 * @param shortName
	 * @param content
	 * @param difficulty
	 * @param options
	 * @param labels
	 * @param answerContent
	 * @param analyse
	 * @return
	 */
	@RequestMapping(value="/simple/save", method=RequestMethod.POST)
	public Result save(String shortName, String content, Integer difficulty, String options, String labels, 
			String answerContent, String analyse) {
		return questionSimpleService.save(shortName, content, difficulty, options, labels, answerContent, analyse);
	}
	
	/**
	 * 更新单选题信息
	 * @param id
	 * @param shortName
	 * @param content
	 * @param difficulty
	 * @param options
	 * @param labels
	 * @param answerContent
	 * @param analyse
	 * @return
	 */
	@RequestMapping(value="/simple/update", method=RequestMethod.POST)
	public Result update(String id, String shortName, String content, Integer difficulty, String options, String labels, 
			String answerContent, String analyse) {
		return questionSimpleService.update(id, shortName, content, difficulty, options, labels, answerContent, analyse);
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
		json.setId(questionSimple.getId());
		json.setLabelId(questionSimple.getLabel().getId());
		json.setOptions(questionSimple.getSimpleOptions());
		json.setAuthorId(questionSimple.getAuthor().getUid());
		json.setAuthorName(questionSimple.getAuthor().getName());
		return json;
	}

}
