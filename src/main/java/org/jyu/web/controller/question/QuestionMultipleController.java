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
import org.jyu.web.dto.question.QuestionMultipleJson;
import org.jyu.web.entity.question.Answer;
import org.jyu.web.entity.question.Option;
import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.entity.question.QuestionMultiple;
import org.jyu.web.service.question.QuestionMultipleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class QuestionMultipleController {
	
	@Autowired
	private QuestionMultipleService multipleService;
	
	/**
	 * 跳转多选题页面
	 * @param mv    ModelAndView
	 * @return   ModelAndView
	 */
	@RequestMapping(value="/multiple_list_student", method=RequestMethod.GET)
	public ModelAndView multiple_list_student(ModelAndView mv) {
		mv.setViewName("/question/student/multiple_list.html");
		return mv;
	}
	
	/**
	 * 修改多选题界面
	 * @param mv    ModelAndView
	 * @param id   单选题主键
	 * @return   ModelAndView
	 */
	@RequestMapping(value="/multiple_up_html", method=RequestMethod.GET)
	public ModelAndView multiple_up_html(ModelAndView mv, String id) {
		mv.setViewName("question/teacher/multiple_up.html");
		return mv;
	}
	
	/**
	 * 多选题详情页
	 * @param mv   ModelAndView
	 * @param id   多选题主键
	 * @return   ModelAndView
	 */
	@RequestMapping(value="/multiple_info_html", method=RequestMethod.GET)
	public ModelAndView multiple_info_html(ModelAndView mv, String id) {
		mv.setViewName("question/student/multiple_info.html");
		return mv;
	}

	/**
	 * 保存多选题
	 * @param shortName   简述
	 * @param content   问题主体
	 * @param difficulty  难度
	 * @param labelIds    标签主键（逗号）
	 * @param options   选项（逗号分割）
	 * @param answerContent  参考答案（逗号分割）
	 * @param analyse   答案分析
	 * @return   Result对象
	 */
	@RequiresPermissions(value={"question:add"})
	@RequestMapping(value="/multiple/save", method=RequestMethod.POST)
	public Result save(String shortName, String content, Integer difficulty, String labelIds, String options,
			String answerContent, String analyse) {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		if (userId == null) {
			return new Result(false, "请登陆");
		}
		List<String> list = new ArrayList<>();
		String[] labelId = labelIds.split(",");
		for (int i = 0; i < labelId.length; i++) {
			if (!labelId[i].equals("")) {
				list.add(labelId[i]);
			}
		}
		return multipleService.save(shortName, content, difficulty, list, answerContent, analyse, options, userId);
	}
	
	/**
	 * 删除多选题
	 * @param id  主键
	 * @return  Result对象
	 */
	@RequiresPermissions(value={"question:delete"})
	@RequestMapping(value="/multiple/delete", method=RequestMethod.POST)
	public Result deleteById(String id) {
		return multipleService.delete(id);
	}
	
	/**
	 * 更新多选题
	 * @param id    主键
	 * @param shortName   简述
	 * @param content     题目
	 * @param difficulty  难度
	 * @param labelIds     问题标签（逗号分割）
	 * @param options      选项（逗号分割）
	 * @param answerContent  参考答案
	 * @param analyse     参考答案分析
	 * @return  Result对象
	 */
	@RequiresPermissions(value={"question:add"})
	@RequestMapping(value="/multiple/update",  method=RequestMethod.POST)
	public Result update(String id, String shortName, String content, Integer difficulty, String labelIds, String options,
			String answerContent, String analyse) {
		List<String> list = new ArrayList<>();
		String[] labelId = labelIds.split(",");
		for (int i = 0; i < labelId.length; i++) {
			if (!labelId[i].equals("")) {
				list.add(labelId[i]);
			}
		}
		return multipleService.update(id, shortName, content, difficulty, list, answerContent, analyse, options);
	}
	
	/**
	 * 通过主键查找多选题
	 * @param id  主键
	 * @return  QuestionMultipleJson对象
	 */
	@RequiresAuthentication
	@RequestMapping(value="/multiple/findById",  method=RequestMethod.GET)
	public QuestionMultipleJson getById(String id) {
		return convert(multipleService.findById(id));
	}
	
	/**
	 * 分页
	 * @param pageNumber  页码
	 * @param pageSize   分页大小
	 * @param sortOrder   排序
	 * @return  Map集合
	 */
	@RequiresPermissions(value={"question:query"})
	@RequestMapping(value="/multiple/all",  method=RequestMethod.GET)
	public Map<String, Object> getPageJson(int pageNumber, int pageSize, String sortOrder) {
		return multipleService.list(pageNumber, pageSize, sortOrder);
	}
	
	/**
	 * 分页查询登陆用户提交的多选题
	 * @param pageNumber   页码
	 * @param pageSize     分页大小
	 * @param sortOrder    排序
	 * @return   Map集合
	 */
	@RequiresAuthentication
	@RequestMapping(value="/multiple/own", method=RequestMethod.GET)
	public Map<String, Object> getOwnQuestionProgram(int pageNumber, int pageSize, String sortOrder) {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		
		Map<String, Object> map = new HashMap<>();
		if (userId == null) {
			return map;
		}
		return multipleService.getPageByUser(pageNumber, pageSize, sortOrder, userId);
	}
	
	/**
	 * 获取某个多选题的参考答案
	 * @param id  主键
	 * @return    AnswerJson对象
	 */
	@RequiresAuthentication
	@RequestMapping(value="/multiple/answer", method=RequestMethod.GET)
	public AnswerJson getAnswer(String id) {
		Answer answer = multipleService.findById(id).getAnswer();
		AnswerJson json = new AnswerJson();
		json.setId(answer.getId());
		json.setAnalyse(answer.getAnalyse());
		json.setContent(answer.getContent());
		return json;
	}
	
	/**
	 * 判断用户提交的答案
	 * @param qid    多选题主键
	 * @param answer   答案
	 * @return   Result对象
	 */
	@GetMapping(value="/multiple/judge")
	public Result judge(String qid, String answer) {
		QuestionMultiple multiple = multipleService.findById(qid);
		if (multiple == null) {
			return new Result(false, "题目不存在");
		}
		if (multiple.getAnswer() == null || multiple.getAnswer().getContent() == null) {
			return new Result(false, "参考答案不存在");
		}
		String answerContent = multiple.getAnswer().getContent();
		
		String[] tmp = answer.split(",");
		String[] answers = answerContent.split(",");
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].trim().length() == 0) {
				continue;
			}
			boolean a = true;
			for(int j = 0; j < answers.length; j++) {
				if(answers[i].equals(tmp[i])) {
					a = false;
				}
			}
			return new Result(false, multiple.getAnswer().getAnalyse());
		}
		return new Result(true, multiple.getAnswer().getAnalyse());
	}
	
	
	QuestionMultipleJson convert(QuestionMultiple multiple) {
		if (multiple == null) {
			return null;
		}
		QuestionMultipleJson json = new QuestionMultipleJson();
		json.setAuthorId(multiple.getAuthor().getUid());
		json.setAuthorName(multiple.getAuthor().getName());
		json.setContent(multiple.getContent());
		json.setCreateTime(multiple.getCreateTime());
		json.setDifficulty(multiple.getDifficulty());
		json.setId(multiple.getId());
		json.setShortName(multiple.getShortName());
		
		List<QuestionLabel> labels = multiple.getQuestionLabels();
		String[] labels_array = new String[labels.size()];
		for (int i = 0; i < labels.size(); i++) {
			labels_array[i] = labels.get(i).getName();
		}
		json.setLabels(Arrays.toString(labels_array));
		
		List<String> list = new ArrayList<>();
		for (Option option : multiple.getOptions()) {
			list.add(option.getContent());
		}
		json.setOptions(list.toString());
		json.setType(multiple.getType());
		return json;
	}
}
