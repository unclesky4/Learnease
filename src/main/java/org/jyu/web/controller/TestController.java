package org.jyu.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	//@RequiresPermissions({"text"})
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String test() {
		return "hello world";
	}

}
