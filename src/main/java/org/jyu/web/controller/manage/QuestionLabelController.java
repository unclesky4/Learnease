package org.jyu.web.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.manage.LabelJson;
import org.jyu.web.service.manage.QuestionLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
		
		List<LabelJson> list = service.list();
		for (LabelJson questionLabelJson : list) {
			Map<String, String> map = new HashMap<>();
			map.put("id", questionLabelJson.getId());
			map.put("text", questionLabelJson.getName());
			data.add(map);
		}
		return data;
	}

	/**
	 * 保存问题标签
	 * @param name   标签名
	 * @return
	 */
	@PostMapping(value="/questionLabel/add")
	public Result save(String name) {
		return service.save(name);
	}
	
	/**
	 * 更新问题标签
	 * @param id     主键
	 * @param name   标签名
	 * @return
	 */
	@PostMapping(value="/questionLabel/update")
	public Result update(String id, String name) {
		return service.update(id, name);
	}
	
	/**
	 * 删除问题标签
	 * @param id   主键
	 * @return
	 */
	@PostMapping(value="/questionLabel/delete")
	public Result deleteById(String id) {
		return service.deleteById(id);
	}
	
	/**
	 * 分页查询
	 * @param pageNumber   页码
	 * @param pageSize     每页显示条数
	 * @return
	 */
	@GetMapping(value="/questionLabel/page_json")
	public Map<String, Object> getPageJson(Integer pageNumber, Integer pageSize) {
		return service.pageJson(pageNumber, pageSize);
	}
}
