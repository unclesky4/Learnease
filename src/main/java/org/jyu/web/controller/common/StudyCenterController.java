package org.jyu.web.controller.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class StudyCenterController {
	
	@RequestMapping(value="/study_center", method=RequestMethod.GET)
	public ModelAndView studyCenter(ModelAndView mv) {
		mv.setViewName("/index.html");
		return mv;
	}
	
	@RequestMapping(value="/course_student", method=RequestMethod.GET)
	public ModelAndView course_student(ModelAndView mv) {
		mv.setViewName("/question/student/mycourse.html");
		return mv;
	}
	
	@RequestMapping(value="/solutionset_student", method=RequestMethod.GET)
	public ModelAndView solutionset_student(ModelAndView mv) {
		mv.setViewName("/question/student/mysolutionset.html");
		return mv;
	}
	
	@RequestMapping(value="/test_student", method=RequestMethod.GET)
	public ModelAndView test_student(ModelAndView mv) {
		mv.setViewName("/question/student/mytest.html");
		return mv;
	}
	
	@RequestMapping(value="/problem_student", method=RequestMethod.GET)
	public ModelAndView problem_student(ModelAndView mv) {
		mv.setViewName("/question/student/myproblem.html");
		return mv;
	}

}
