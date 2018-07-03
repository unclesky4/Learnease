package org.jyu.web.service.authority;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.authority.TeacherJson;

public interface TeacherService {
	
	/**
	 * 通过工号查询
	 * @param idCard    职工号
	 * @return  TeacherJson对象
	 */
	TeacherJson findByIdCard(String idCard);
	
	/**
	 * 通过主键查询
	 * @param id    教师主键
	 * @return  TeacherJson对象
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
	 * @return  Result对象
	 */
	Result save(String name,String sex,String idCard,String subject, String phone, String userId);
	
	/**
	 * 修改教师信息
	 * @param tid    教师主键
	 * @param name  姓名
	 * @param sex  性别
	 * @param idCard  工号
	 * @param subject  学科
	 * @param phone  电话
	 * @param status  状态  0:未审核   1:已审核  2: 审核通过
	 * @return   Result对象
	 */
	Result update(String tid, String name,String sex,String idCard,String subject,
			String phone, Integer status);
	
	/**
	 * 
	 * 删除教师
	 * @param ids    主键（逗号分割）
	 * @return: Result对象
	 *
	 */
	Result delete(List<String> ids);
	
	/**
	 * 通过主键删除
	 * @param id   主键
	 * @return  Result对象
	 */
	Result deleteById(String id);
	
	/**
	 * 
	 * 分页查询
	 * @param pageNumber   页码
	 * @param pageSize     分页大小
	 * @param sortOrder    排序
	 * @param searchText   查询字段
	 * @return: Map集合
	 *
	 */
	Map<String, Object> findTeacherByPage(Integer pageNumber, Integer pageSize, String sortOrder, String searchText);

	/**
	 * 查询待审核的教师信息
	 * @param pageNumber    页码
	 * @param pageSize      分页大小
	 * @return  Map集合
	 */
	Map<String, Object> getNeedToVerify(Integer pageNumber, Integer pageSize);
}
