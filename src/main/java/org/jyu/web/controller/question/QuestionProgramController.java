package org.jyu.web.controller.question;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionProgramJson;
import org.jyu.web.service.question.QuestionProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class QuestionProgramController {
	
	@Autowired
	private QuestionProgramService questionProgramService;
	
	
	@RequestMapping(value="program_list_student", method=RequestMethod.GET)
	public ModelAndView program_list_student(ModelAndView mv) {
		mv.setViewName("/question/student/program_list.html");
		return mv;
	}
	
	@RequestMapping(value="program_add_student", method=RequestMethod.GET)
	public ModelAndView program_add_student(ModelAndView mv) {
		mv.setViewName("/question/student/program_add.html");
		return mv;
	}
	
	@RequestMapping(value="program_up_student", method=RequestMethod.GET)
	public ModelAndView program_up_student(ModelAndView mv) {
		mv.setViewName("/question/student/program_up.html");
		return mv;
	}
	
	@RequestMapping(value="program_query_student", method=RequestMethod.GET)
	public ModelAndView program_query_student(ModelAndView mv) {
		mv.setViewName("/question/student/program_query.html");
		return mv;
	}
	
	/**
	 * 保存编程题
	 * @param shortName  题目简述
	 * @param content  题目主干
	 * @param difficulty  难度
	 * @param description  描述
	 * @param input  输入
	 * @param output  输出
	 * @param exampleInput  示例输入
 	 * @param exampleOutput  示例输出
	 * @param hint   提示
	 * @param answerContent  参考答案内容
	 * @param analyse  分析
	 * @param labels  标签（逗号分割）
	 * @return
	 */
	@RequestMapping(value="/program/save", method=RequestMethod.POST)
	public Result save(String shortName, String content, Integer difficulty, String description, 
			String input, String output, String exampleInput, String exampleOutput, String hint, 
			String answerContent, String analyse, String labels) {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		return questionProgramService.save(shortName, content, difficulty, description, input, output, exampleInput, 
				exampleOutput, hint, answerContent, analyse, labels, userId);
	}
	
	/**
	 * 删除编程题
	 * @return
	 */
	@RequestMapping(value="/program/delete", method=RequestMethod.POST)
	public Result delete(String id) {
		return questionProgramService.delete(id);
	}
	
	/**
	 * 修改编程题
	 * @param id
	 * @param shortName  题目简述
	 * @param content  题目主干
	 * @param difficulty  难度
	 * @param description  描述
	 * @param input  输入
	 * @param output  输出
	 * @param exampleInput  示例输入
 	 * @param exampleOutput  示例输出
	 * @param hint   提示
	 * @param answerContent  参考答案内容
	 * @param analyse  分析
	 * @param labels  标签（逗号分割）
	 * @return
	 */
	@RequestMapping(value="/program/update", method=RequestMethod.POST)
	public Result update(String id, String shortName, String content, Integer difficulty,
			String description, String input, String output, String exampleInput, String exampleOutput, 
			String hint, String answerContent, String analyse, String labels) {
		return questionProgramService.update(id, shortName, content, difficulty, description, input, output, 
				exampleInput, exampleOutput, hint, answerContent, analyse, labels);
	}
	
	/**
	 * 通过主键查询编程题
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/program/getById", method=RequestMethod.GET)
	public QuestionProgramJson getById(String id) {
		return null;
	}
	
	/**
	 * 分页查询
	 * @param pageNumber  页码
	 * @param pageSize  每页显示条数
	 * @param sortOrder  排序
	 * @return
	 */
	@RequestMapping(value="/program/all", method=RequestMethod.GET)
	public Map<String, Object> getAllQuestionBlank(int pageNumber, int pageSize, String sortOrder) {
		return questionProgramService.list(pageNumber, pageSize, sortOrder);
	}

}
