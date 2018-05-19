package org.jyu.web.controller.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CommonController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView indexHtml(ModelAndView mv) {
		mv.setViewName("index.html");
		return mv;
	}

	@RequestMapping(value="/user_center", method=RequestMethod.GET)
	public ModelAndView userCenter(ModelAndView mv) {
		mv.setViewName("authority/user_center.html");
		return mv;
	}
}
