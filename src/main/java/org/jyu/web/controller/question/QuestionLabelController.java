package org.jyu.web.controller.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.service.question.QuestionLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionLabelController {
	
	@Autowired
	private QuestionLabelService service;
	
	/**
	 * 获取符合select2的所有问题标签
	 * @return
	 */
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

	/**
	 * 保存问题标签
	 * @param name
	 * @return
	 */
	@PostMapping(value="/questionLabel/add")
	public Result save(String name) {
		return service.save(name);
	}
	
	/**
	 * 更新问题标签
	 * @param id
	 * @param name
	 * @return
	 */
	@PostMapping(value="/questionLabel/update")
	public Result update(String id, String name) {
		return service.update(id, name);
	}
	
	/**
	 * 删除问题标签
	 * @param id
	 * @return
	 */
	@PostMapping(value="/questionLabel/deleteById")
	public Result deleteById(String id) {
		return service.deleteById(id);
	}
	
}
