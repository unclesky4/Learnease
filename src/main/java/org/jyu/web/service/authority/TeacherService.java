package org.jyu.web.service.authority;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.authority.TeacherJson;

public interface TeacherService {
	
	/**
	 * 通过工号查询
	 * @param idCard
	 * @return
	 */
	TeacherJson findByIdCard(String idCard);
	
	/**
	 * 通过主键查询
	 * @param id
	 * @return
	 */
	TeacherJson findById(String id);
	
	/**
	 * 保存教师
	 * @param name  姓名
	 * @param sex  性别
	 * @param idCard  工号
	 * @param subject  学科
	 * @param phone  电话
	 * @param userId 用户主键
	 * @return
	 */
	Result save(String name,String sex,String idCard,String subject, String phone, String userId);
	
	/**
	 * 修改教师信息
	 * @param tid
	 * @param name  姓名
	 * @param sex  性别
	 * @param idCard  工号
	 * @param subject  学科
	 * @param phone  电话
	 * @param status  状态  0:未审核   1:已审核  2: 审核通过
	 * @return
	 */
	Result update(String tid, String name,String sex,String idCard,String subject,
			String phone, Integer status);
	
	/**
	 * 
	 * @Description: 删除教师
	 * @param: @param ids
	 * @return: Result
	 * @throws
	 *
	 */
	Result delete(List<String> ids);
	
	/**
	 * 
	 * @Description: 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @param searchText
	 * @return: Map<String,Object>
	 * @throws
	 *
	 */
	Map<String, Object> findTeacherByPage(Integer pageNumber, Integer pageSize, String sortOrder, String searchText);
}
