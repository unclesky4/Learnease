package org.jyu.web.controller.question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.AnswerJson;
import org.jyu.web.dto.question.QuestionProgramJson;
import org.jyu.web.entity.question.Answer;
import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.entity.question.QuestionProgram;
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
	
	/**
	 * 编程题列表  -- study
	 * @param mv    ModelAndView
	 * @return    ModelAndView
	 */
	@RequestMapping(value="/program_list_student", method=RequestMethod.GET)
	public ModelAndView program_list_student(ModelAndView mv) {
		mv.setViewName("question/student/program_list.html");
		return mv;
	}
	
	/**
	 * 编程题信息
	 * @param mv   ModelAndView
	 * @return   ModelAndView
	 */
	@RequestMapping(value="/program_info_html", method=RequestMethod.GET)
	public ModelAndView program_info_html(ModelAndView mv) {
		mv.setViewName("question/student/program_info.html");
		return mv;
	}
	
	/**
	 * 修改编程题界面
	 * @param mv   ModelAndView
	 * @param id   判断题主键
	 * @return   ModelAndView
	 */
	@RequestMapping(value="/program_up_html", method=RequestMethod.GET)
	public ModelAndView program_up_html(ModelAndView mv, String id) {
		mv.setViewName("question/teacher/program_up.html");
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
	 * @param labelIds  标签（逗号分割）
	 * @return  Result对象
	 */
	@RequiresPermissions(value={"question:add"})
	@RequestMapping(value="/program/save", method=RequestMethod.POST)
	public Result save(String shortName, String content, Integer difficulty, String description, 
			String input, String output, String exampleInput, String exampleOutput, String hint, 
			String answerContent, String analyse, String labelIds) {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		List<String> list = new ArrayList<>();
		String[] labelId = labelIds.split(",");
		for (int i = 0; i < labelId.length; i++) {
			if (!labelId[i].equals("")) {
				list.add(labelId[i]);
			}
		}
		return questionProgramService.save(shortName, content, difficulty, description, input, output, exampleInput, 
				exampleOutput, hint, answerContent, analyse, list, userId);
	}
	
	/**
	 * 删除编程题
	 * @param id  主键
	 * @return   Result对象
	 */
	@RequiresPermissions(value={"question:delete"})
	@RequestMapping(value="/program/delete", method=RequestMethod.POST)
	public Result delete(String id) {
		return questionProgramService.delete(id);
	}
	
	/**
	 * 修改编程题 
	 * @param id   主键
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
	 * @param labelIds  标签（逗号分割）
	 * @return   Result对象
	 */
	@RequiresPermissions(value={"question:update"})
	@RequestMapping(value="/program/update", method=RequestMethod.POST)
	public Result update(String id, String shortName, String content, Integer difficulty,
			String description, String input, String output, String exampleInput, String exampleOutput, 
			String hint, String answerContent, String analyse, String labelIds) {
		List<String> list = new ArrayList<>();
		String[] labelId = labelIds.split(",");
		for (int i = 0; i < labelId.length; i++) {
			if (!labelId[i].equals("")) {
				list.add(labelId[i]);
			}
		}
		return questionProgramService.update(id, shortName, content, difficulty, description, input, output, 
				exampleInput, exampleOutput, hint, answerContent, analyse, list);
	}
	
	/**
	 * 通过主键查询编程题
	 * @param id  主键
	 * @return    QuestionProgramJson对象
	 */
	@RequiresAuthentication
	@RequestMapping(value="/program/getById", method=RequestMethod.GET)
	public QuestionProgramJson getById(String id) {
		return convert(questionProgramService.findById(id));
	}
	
	/**
	 * 分页查询
	 * @param pageNumber  页码
	 * @param pageSize  每页显示条数
	 * @param sortOrder  排序
	 * @return  Map集合
	 */
	@RequiresAuthentication
	@RequestMapping(value="/program/all", method=RequestMethod.GET)
	public Map<String, Object> getAllQuestionProgram(int pageNumber, int pageSize, String sortOrder) {
		return questionProgramService.list(pageNumber, pageSize, sortOrder);
	}
	
	/**
	 * 分页查询登陆用户提交的编程题
	 * @param pageNumber   页码
	 * @param pageSize     分页大小
	 * @param sortOrder    排序
	 * @return    Map集合
	 */
	@RequiresAuthentication
	@RequestMapping(value="/program/own", method=RequestMethod.GET)
	public Map<String, Object> getOwnQuestionProgram(int pageNumber, int pageSize, String sortOrder) {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		
		Map<String, Object> map = new HashMap<>();
		if (userId == null) {
			return map;
		}
		return questionProgramService.getPageByUser(pageNumber, pageSize, sortOrder, userId);
	}
	
	/**
	 * 获取某个填编程题的参考答案
	 * @param id    主键
	 * @return   AnswerJson对象
	 */
	@RequiresAuthentication
	@RequestMapping(value="/program/answer", method=RequestMethod.GET)
	public AnswerJson getAnswer(String id) {
		Answer answer = questionProgramService.findById(id).getAnswer();
		AnswerJson json = new AnswerJson();
		json.setId(answer.getId());
		json.setAnalyse(answer.getAnalyse());
		json.setContent(answer.getContent());
		return json;
	}

	QuestionProgramJson convert(QuestionProgram questionProgram) {
		if (questionProgram == null) {
			return null;
		}
		QuestionProgramJson json = new QuestionProgramJson();
		json.setContent(questionProgram.getContent());
		json.setDescription(questionProgram.getDescription());
		json.setDifficulty(questionProgram.getDifficulty());
		json.setExampleInput(questionProgram.getExampleInput());
		json.setExampleOutput(questionProgram.getExampleOutput());
		json.setHint(questionProgram.getHint());
		json.setId(questionProgram.getId());
		json.setInput(questionProgram.getInput());
		json.setOutput(questionProgram.getOutput());
		json.setShortName(questionProgram.getShortName());
		json.setCreateTime(questionProgram.getCreateTime());
		List<QuestionLabel> labels = questionProgram.getQuestionLabels();
		String[] labels_array = new String[labels.size()];
		for (int i = 0; i < labels.size(); i++) {
			labels_array[i] = labels.get(i).getName();
		}
		json.setLabels(Arrays.toString(labels_array));
		return json;
	}
}
