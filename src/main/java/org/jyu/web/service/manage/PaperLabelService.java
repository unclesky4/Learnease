package org.jyu.web.service.manage;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.entity.paper.PaperLabel;

public interface PaperLabelService {

	Result save(String name);
	
	Result update(String id, String name);
	
	Result deleteByid(String id);
	
	/**
	 * 动态分页查询
	 * @param pageNumber  页码
	 * @param pageSize    显示条数
	 * @param sortOrder   排序（desc/asc）
	 * @return
	 */
	Map<String, Object> pageJson(Integer pageNumber, Integer pageSize, String sortOrder);
	
	List<PaperLabel> list();
}
