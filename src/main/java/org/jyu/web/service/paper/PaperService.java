package org.jyu.web.service.paper;

import java.util.Map;

import org.jyu.web.dto.Result;

public interface PaperService {
	
	/**
	 * @param name   试卷名
	 * @param status  是否公开  0：否  1：是
	 * @param userId   用户主键
	 * @param questionIds    问题主键（逗号分割）
	 * @param questionScore  问题对应的分值（Map类型）
	 * @param paperLabelIds  试卷标签主键（逗号分割）
	 * @return
	 */
	Result save(String name, Integer status, String userId, String questionIds, Map<String, Integer> questionScore,
			String paperLabelIds);

	/**
	 * @param id  试卷主键
	 * @param name   试卷名
	 * @param status      是否公开  0：否  1：是
	 * @param questionIds  问题主键（逗号分割）
	 * @param questionScore  问题对应的分值（Map类型）
	 * @param paperLabelIds    试卷标签主键（逗号分割）
	 * @return
	 */
	Result update(String id, String name, Integer status, String questionIds, Map<String, Integer> questionScore, String paperLabelIds);
	
	/**
	 * 删除试卷
	 * @param id  试卷主键
	 * @return
	 */
	Result deleteById(String id);
	
	/**
	 * 动态分页查询
	 * @param pageNumber  页码
	 * @param pageSize    显示条数
	 * @param sortOrder   排序（desc/asc）
	 * @param searchText   搜索字段
	 * @return
	 */
	Map<String, Object> pageJson(Integer pageNumber, Integer pageSize, String sortOrder, String searchText);
}
