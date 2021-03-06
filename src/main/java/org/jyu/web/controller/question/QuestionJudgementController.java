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
import org.jyu.web.dto.question.QuestionJudgementJson;
import org.jyu.web.entity.question.Answer;
import org.jyu.web.entity.question.Option;
import org.jyu.web.entity.question.QuestionJudgement;
import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.service.question.QuestionJudgementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class QuestionJudgementController {
	
	@Autowired
	private QuestionJudgementService service;
	
	/**
	 * 记载判断题列表页面
	 * @param mv     ModelAndView
	 * @return   ModelAndView
	 */
	@RequestMapping(value="/judgement_list_student", method=RequestMethod.GET)
	public ModelAndView judgement_list_student(ModelAndView mv) {
		mv.setViewName("question/student/judgement_list.html");
		return mv;
	}
	
	/**
	 * 修改判断题界面
	 * @param mv   ModelAndView
	 * @param id   判断题主键
	 * @return   ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value="/judgement_up_html", method=RequestMethod.GET)
	public ModelAndView judgement_up_html(ModelAndView mv, String id) {
		mv.setViewName("question/teacher/judgement_up.html");
		return mv;
	}

	/**
	 * 保存判断题
	 * @param shortName 问题简述
	 * @param content   题目
	 * @param difficulty   难度
	 * @param labelIds   标签主键（逗号分割）
	 * @param answerContent  参考答案(对/错)
	 * @param analyse   参考答案分析
	 * @return  Result对象
	 */
	@RequiresPermissions(value={"question:add"})
	@RequestMapping(value="/judgement/save", method=RequestMethod.POST)
	public Result save(String shortName, String content, Integer difficulty, String labelIds, String answerContent, String analyse) {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		List<String> label_list = new ArrayList<>();
		String[] label_array = labelIds.split(",");
		for (int i = 0; i < label_array.length; i++) {
			if (label_array[i].equals("")) {
				continue;
			}
			label_list.add(label_array[i]);
		}
		return service.save(shortName, content, difficulty, userId, label_list, answerContent, analyse);
	}
	
	/**
	 * 删除判断题
	 * @param id   主键
	 * @return    Result对象
	 */
	@RequiresPermissions(value={"question:delete"})
	@RequestMapping(value="/judgement/delete", method=RequestMethod.POST)
	public Result deleteById(String id) {
		return service.deleteById(id);
	}
	
	/**
	 * 更新判断题
	 * @param id     主键
	 * @param shortName  问题简述
	 * @param content   题目
	 * @param difficulty  难度
	 * @param labelIds   问题标签主键（逗号分割）
	 * @param options    选项（对+错）
	 * @param answerContent   参考答案
	 * @param analyse   参考答案解析
	 * @return   Result对象
	 */
	@RequiresPermissions(value={"question:update"})
	@RequestMapping(value="/judgement/update", method=RequestMethod.POST)
	public Result update(String id, String shortName, String content, Integer difficulty, String labelIds, String options,
			String answerContent, String analyse) {
		List<String> label_list = new ArrayList<>();
		String[] label_array = labelIds.split(",");
		for (int i = 0; i < label_array.length; i++) {
			if (label_array[i].equals("")) {
				continue;
			}
			label_list.add(label_array[i]);
		}
		return service.update(id, shortName, content, difficulty, label_list, answerContent, analyse);
	}
	
	/**
	 * 通过主键查找判断题
	 * @param id   主键
	 * @return   QuestionJudgementJson对象
	 */
	@RequiresAuthentication
	@RequestMapping(value="/judgement/getById", method=RequestMethod.GET)
	public QuestionJudgementJson getById(String id) {
		return convert(service.findById(id));
	}
	
	/**
	 * 分页
	 * @param pageNumber  页码
	 * @param pageSize     分页大小
	 * @param sortOrder   排序
	 * @return  Map集合
	 */
	@RequiresPermissions(value={"question:query"})
	@RequestMapping(value="/judgement/all", method=RequestMethod.GET)
	public Map<String, Object> list(int pageNumber, int pageSize, String sortOrder) {
		return service.list(pageNumber, pageSize, sortOrder);
	}
	
	/**
	 * 判断用户提交的答案是否正确
	 * @param qid        判断题主键
	 * @param answercontent   答案
	 * @return   Result对象
	 */
	@RequiresAuthentication
	@RequestMapping(value="/judgement/judge",method=RequestMethod.POST)
	public Result judgeAnswer(String qid, String answercontent) {
		return null;
	}
	
	/**
	 * 分页查询登陆用户提交的编程题
	 * @param pageNumber     页码
	 * @param pageSize      分页大小
	 * @param sortOrder    排序
	 * @return   Map集合
	 */
	@RequiresAuthentication
	@RequestMapping(value="/judgement/own", method=RequestMethod.GET)
	public Map<String, Object> getOwnQuestionProgram(int pageNumber, int pageSize, String sortOrder) {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		
		Map<String, Object> map = new HashMap<>();
		if (userId == null) {
			return map;
		}
		return service.getPageByUser(pageNumber, pageSize, sortOrder, userId);
	}
	
	/**
	 * 获取某个判断题的参考答案
	 * @param id    判断题主键
	 * @return      AnswerJson对象
	 */
	@RequiresAuthentication
	@RequestMapping(value="/judgement/answer", method=RequestMethod.GET)
	public AnswerJson getAnswer(String id) {
		Answer answer = service.findById(id).getAnswer();
		AnswerJson json = new AnswerJson();
		json.setId(answer.getId());
		json.setAnalyse(answer.getAnalyse());
		json.setContent(answer.getContent());
		return json;
	}
	
	QuestionJudgementJson convert(QuestionJudgement questionJudgement) {
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
		return json;
	}
}
