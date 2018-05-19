package org.jyu.web.controller.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.AnswerJson;
import org.jyu.web.dto.question.QuestionSimpleJson;
import org.jyu.web.entity.authority.User;
import org.jyu.web.entity.question.Answer;
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
	
	/**
	 * 单选题列表  -- study
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/simple_list_student", method=RequestMethod.GET)
	public ModelAndView simple_list_student(ModelAndView mv) {
		mv.setViewName("question/student/simple_list.html");
		return mv;
	}
	
	/**
	 * 修改单选题界面
	 * @param mv
	 * @param id   单选题主键
	 * @return
	 */
	@RequestMapping(value="/simple_up_html", method=RequestMethod.GET)
	public ModelAndView simple_up_html(ModelAndView mv, String id) {
		mv.setViewName("question/teacher/simple_up.html");
		mv.addObject("simple", questionSimpleService.findById(id));
		return mv;
	}
	
	/**
	 * 单选题信息界面
	 * @param mv
	 * @param id  单选题主键
	 * @return
	 */
	@RequestMapping(value="/simple_info_html", method=RequestMethod.GET)
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
	@RequiresPermissions(value={"question:add"})
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
	@RequiresPermissions(value={"question:update"})
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
	 * @param id  主键
	 * @return
	 */
	@RequiresPermissions(value={"question:delete"})
	@RequestMapping(value="/simple/delete", method=RequestMethod.POST)
	public Result delete(String id) {
		return questionSimpleService.deleteById(id);
	}
	
	/**
	 * 通过主键查询
	 * @param id
	 * @return
	 */
	//@RequiresAuthentication
	@RequestMapping(value="/simple/findById", method=RequestMethod.GET)
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
	@RequiresPermissions(value={"question:query"})
	@RequestMapping(value="/simple/all", method=RequestMethod.GET)
	public Map<String, Object> getPageJson(int pageNumber, int pageSize, String sortOrder) {
		return questionSimpleService.list(pageNumber, pageSize, sortOrder);
	}
	
	/**
	 * 判断用户提交的答案
	 * @param qid
	 * @param answercontent
	 * @return   返回判断结果及参考答案分析
	 */
	@RequiresAuthentication
	@RequestMapping(value="/simple/judge",method=RequestMethod.GET)
	public Result judgeAnswer(String qid, String answercontent) {
		QuestionSimple simple = questionSimpleService.findById(qid);
		if (simple == null) {
			return new Result(false, "题目不存在");
		}
		if (simple.getSimpleAnswer() == null) {
			return new Result(false, "参考答案不存在");
		}
		if (simple.getSimpleAnswer().getContent().equals(answercontent)) {
			return new Result(true, simple.getSimpleAnswer().getAnalyse());
		}
		return new Result(false, simple.getSimpleAnswer().getAnalyse());
	}
	
	/**
	 * 分页查询登陆用户提交的单选题
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping(value="/simple/own", method=RequestMethod.GET)
	public Map<String, Object> getOwnQuestionSimple(int pageNumber, int pageSize, String sortOrder) {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		
		Map<String, Object> map = new HashMap<>();
		if (userId == null) {
			return map;
		}
		return questionSimpleService.getPageByUser(pageNumber, pageSize, sortOrder, userId);
	}
	
	/**
	 * 获取某个单选题的参考答案
	 * @param id
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping(value="/simple/answer", method=RequestMethod.GET)
	public AnswerJson getAnswer(String id) {
		Answer answer = questionSimpleService.findById(id).getSimpleAnswer();
		AnswerJson json = new AnswerJson();
		json.setId(answer.getId());
		json.setAnalyse(answer.getAnalyse());
		json.setContent(answer.getContent());
		return json;
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
		json.setLabels(labels.toString());
		List<String> options = new ArrayList<>();
		for (Option option : questionSimple.getSimpleOptions()) {
			options.add(option.getContent());
		}
		json.setOptions(options.toString());
		
		User user = questionSimple.getAuthor();
		if (user != null) {
			json.setAuthorId(questionSimple.getAuthor().getUid());
			json.setAuthorName(questionSimple.getAuthor().getName());
		}
		return json;
	}

}
