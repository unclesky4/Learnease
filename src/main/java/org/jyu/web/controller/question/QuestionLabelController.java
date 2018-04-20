package org.jyu.web.controller.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.service.question.QuestionLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionLabelController {
	
	@Autowired
	private QuestionLabelService service;
	
	@RequestMapping(value="/questionLabel/label_select2", method=RequestMethod.GET)
	public List<Map<String, String>> label_select2() {
		List<Map<String, String>> data = new ArrayList<>();
		
		List<QuestionLabel> list = service.list();
		for (QuestionLabel questionLabel : list) {
			Map<String, String> map = new HashMap<>();
			map.put("id", questionLabel.getId());
			map.put("text", questionLabel.getName());
			data.add(map);
		}
		return data;
	}

}
