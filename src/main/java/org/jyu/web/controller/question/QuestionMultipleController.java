package org.jyu.web.controller.question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionMultipleJson;
import org.jyu.web.entity.question.Option;
import org.jyu.web.entity.question.QuestionMultiple;
import org.jyu.web.service.question.QuestionMultipleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class QuestionMultipleController {
	
	@Autowired
	private QuestionMultipleService multipleService;
	
	@RequestMapping(value="multiple_list_student", method=RequestMethod.GET)
	public ModelAndView multiple_list_student(ModelAndView mv) {
		mv.setViewName("/question/student/multiple_list.html");
		return mv;
	}
	
	@RequestMapping(value="multiple_add_student", method=RequestMethod.GET)
	public ModelAndView multiple_add_student(ModelAndView mv) {
		mv.setViewName("/question/student/multiple_add.html");
		return mv;
	}
	
	@RequestMapping(value="multiple_up_student", method=RequestMethod.GET)
	public ModelAndView multiple_up_student(ModelAndView mv) {
		mv.setViewName("/question/student/multiple_up.html");
		return mv;
	}
	
	@RequestMapping(value="multiple_query_student", method=RequestMethod.GET)
	public ModelAndView multiple_query_student(ModelAndView mv) {
		mv.setViewName("/question/student/multiple_query.html");
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
	 * @return
	 */
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
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/multiple/delete", method=RequestMethod.POST)
	public Result deleteById(String id) {
		return multipleService.delete(id);
	}
	
	/**
	 * 更新多选题
	 * @param id
	 * @param shortName
	 * @param content
	 * @param difficulty
	 * @param labelIds
	 * @param options
	 * @param answerContent
	 * @param analyse
	 * @return
	 */
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
	 * @param id
	 * @return
	 */
	public QuestionMultipleJson getById(String id) {
		return convert(multipleService.findById(id));
	}
	
	/**
	 * 分页
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @return
	 */
	@RequestMapping(value="/multiple/all",  method=RequestMethod.GET)
	public Map<String, Object> getPageJson(int pageNumber, int pageSize, String sortOrder) {
		return multipleService.list(pageNumber, pageSize, sortOrder);
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
		
		List<String> list = new ArrayList<>();
		for (Option option : multiple.getOptions()) {
			list.add(option.getContent());
		}
		json.setOptions(list.toString().replaceAll("[", "").replaceAll("]", ""));
		json.setType(multiple.getType());
		return json;
	}
}
