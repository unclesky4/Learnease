package org.jyu.web.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.Length;
import org.jyu.web.dto.Result;
import org.jyu.web.entity.paper.PaperLabel;
import org.jyu.web.service.manage.PaperLabelService;
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
	@RequiresAuthentication
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
	 * @param name  标签名
	 * @return
	 */
	@RequiresPermissions(value={"paperlabel:add"})
	@PostMapping(value="/paperLabel/add")
	public Result save(@Length(min=1, max=20, message="标签名长度为1-20位")String name) {
		return service.save(name);
	}
	
	/**
	 * 更新试卷标签
	 * @param id   主键
	 * @param name  标签名
	 * @return
	 */
	@RequiresPermissions(value={"paperlabel:update"})
	@PostMapping(value="/paperLabel/update")
	public Result update(String id, @Length(min=1, max=20, message="标签名长度为1-20位")String name) {
		return service.update(id, name);
	}
	
	/**
	 * 删除试卷标签
	 * @param id   主键
	 * @return
	 */
	@RequiresPermissions(value={"paperlabel:delete"})
	@PostMapping(value="/paperLabel/delete")
	public Result deleteById(String id) {
		return service.deleteByid(id);
	}
	
	/**
	 * 分页查询
	 * @param pageNumber   页码
	 * @param pageSize    每页显示条数
	 * @param sortOrder   排序
	 * @return
	 */
	@RequiresPermissions(value={"paperlabel:query"})
	@GetMapping(value="paperLabel/page_json")
	public Map<String, Object> pageJson(Integer pageNumber, Integer pageSize, String sortOrder) {
		return  service.pageJson(pageNumber, pageSize, sortOrder);
	}

}
