package org.jyu.web.controller.paper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.entity.paper.PaperLabel;
import org.jyu.web.service.paper.PaperLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaperLabelController {
	
	@Autowired
	private PaperLabelService service;
	
	/**
	 * 获取符合select2格式的所有试卷标签
	 * @return
	 */
	@GetMapping(value="/paperLabel/label_select2")
	public List<Map<String, String>> label_select2() {
		List<Map<String, String>> data = new ArrayList<>();
		
		List<PaperLabel> list = service.list();
		for (PaperLabel paperLabel : list) {
			Map<String, String> map = new HashMap<>();
			map.put("id", paperLabel.getId());
			map.put("text", paperLabel.getName());
			data.add(map);
		}
		return data;
	}
	
	/**
	 * 保存试卷标签
	 * @param name
	 * @return
	 */
	@PostMapping(value="/paperLabel/add")
	public Result save(String name) {
		return service.save(name);
	}
	
	/**
	 * 更新试卷标签
	 * @param id
	 * @param name
	 * @return
	 */
	@PostMapping(value="/paperLabel/update")
	public Result update(String id, String name) {
		return service.update(id, name);
	}
	
	/**
	 * 删除试卷标签
	 * @param id
	 * @return
	 */
	@PostMapping(value="/paperLabel/deleteById")
	public Result deleteById(String id) {
		return service.deleteByid(id);
	}

}
