package org.jyu.web.controller.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TeacherCenterController {
	
	@RequestMapping(value="/teacher_center", method=RequestMethod.GET)
	public ModelAndView teacherCenter(ModelAndView mv) {
		mv.setViewName("authority/teacher_center.html");
		return mv;
	}
	
	@RequestMapping(value="/question_add", method=RequestMethod.GET)
	public ModelAndView question_add(ModelAndView mv) {
		mv.setViewName("question/teacher/question_add.html");
		return mv;
	}

	@RequestMapping(value="/own_questions", method=RequestMethod.GET)
	public ModelAndView own_questions(ModelAndView mv) {
		mv.setViewName("question/teacher/own_questions.html");
		return mv;
	}
	
	@RequestMapping(value="/question_all", method=RequestMethod.GET)
	public ModelAndView question_all(ModelAndView mv) {
		mv.setViewName("question/teacher/question_all.html");
		return mv;
	}
	
	@RequestMapping(value="/paper_add", method=RequestMethod.GET)
	public ModelAndView paper_add(ModelAndView mv) {
		mv.setViewName("exam/teacher/paper_add.html");
		return mv;
	}
	
	@RequestMapping(value="/own_paper", method=RequestMethod.GET)
	public ModelAndView own_paper(ModelAndView mv) {
		mv.setViewName("exam/teacher/own_paper.html");
		return mv;
	}
	
	@RequestMapping(value="/paper_all", method=RequestMethod.GET)
	public ModelAndView paper_all(ModelAndView mv) {
		mv.setViewName("exam/teacher/paper_all.html");
		return mv;
	}
}
