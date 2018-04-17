package org.jyu.web.controller.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TeacherCenterController {
	
	@RequestMapping(value="/teacher_center", method=RequestMethod.GET)
	public ModelAndView teacherCenter(ModelAndView mv) {
		mv.setViewName("/authority/teacher_center.html");
		return mv;
	}
	
	public ModelAndView andView(ModelAndView mv) {
		return null;
	}

}
