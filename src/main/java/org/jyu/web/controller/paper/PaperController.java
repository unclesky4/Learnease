package org.jyu.web.controller.paper;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jyu.web.dto.Result;
import org.jyu.web.service.paper.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PaperController {

	@Autowired
	private PaperService paperService;
	
	/**
	 * 添加试卷页面
	 * @param mv
	 * @return
	 */
	@GetMapping(value="/paper_add_html")
	public ModelAndView paper_add_html(ModelAndView mv) {
		mv.setViewName("/paper/teacher/paper_add.html");
		return mv;
	}
	
	/**
	 * 保存试卷
	 * @param name   试卷名
	 * @param questions   问题主键（逗号分割）
	 * @param status    公开状态（0：私有，1：公开 ）
	 * @param scores    问题主键对应的分值（逗号分割）
	 * @param labels   试卷标签主键（逗号分割）
	 * @return
	 */
	@RequiresPermissions({"paper:add"})
	@PostMapping(value="/paper/add")
	public Result save(String name, String questions, Integer status, String scores, String labels) {
		
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		if (userId == null) {
			return new Result(false, "请登陆");
		}
		
		Map<String, Integer> questionScore = new HashMap<>();
		String[] questionId = questions.split(",");
		String[] score = scores.split(",");
		if (questionId.length != score.length) {
			return new Result(false, "问题与分值不匹配");
		}
		int size = questionId.length;
		for (int i = 0; i < size; i++) {
			questionScore.put(questionId[i], Integer.parseInt(score[i]));
		}
		return paperService.save(name, status, userId, questions, questionScore, labels);
	}
	
	/**
	 * 更新试卷
	 * @param id   试卷主键
	 * @param name  姓名
	 * @param questions  试题主键
	 * @param status   状态
	 * @param scores   题目分值
	 * @param labels  试卷标签
	 * @return
	 */
	@RequiresPermissions({"paper:update"})
	@PostMapping(value="/paper/update")
	public Result update(String id, String name, String questions, Integer status, String scores, String labels) {
		return null;
	}
}
