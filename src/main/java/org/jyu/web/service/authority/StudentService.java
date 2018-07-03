package org.jyu.web.service.authority;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.authority.StudentJson;

public interface StudentService {

	/**
	 * 保存学生
	 * @param idCard  学号
	 * @param stuName  姓名
	 * @param stuSex  性别
	 * @param stuAcademy  学院专业
	 * @param stuClass  班级
	 * @param stuEntranceTime  入学日期
	 * @param userId  用户主键
	 * @return    Result对象
	 */
	Result save(String idCard,String stuName,String stuSex,String stuAcademy,String stuClass,
			Integer stuEntranceTime,String userId);
	
	/**
	 * 更新
	 * @param stuId   主键
	 * @param idCard   学号
	 * @param stuName   姓名
	 * @param stuSex   性别
	 * @param stuAcademy   学院
	 * @param stuClass   班级
	 * @param stuEntranceTime   入学时间
	 * @param status   审核状态
	 * @return   Result对象
	 */
	Result update(String stuId, String idCard,String stuName,String stuSex,String stuAcademy,String stuClass,
			Integer stuEntranceTime,Integer status);
	
	/**
	 * 批量删除学生
	 * @param ids   主键（逗号分割）
	 * @return   Result对象
	 */
	Result delete(List<String> ids);
	
	/**
	 * 通过主键删除
	 * @param id  主键
	 * @return   Result对象
	 */
	Result delete(String id);
	
	/**
	 * 通过主键查询学生
	 * @param id   主键
	 * @return  StudentJson对象
	 */
	StudentJson findById(String id);

	/**
	 * 通过学号查找学生信息
	 * @param idCard    学号
	 * @return  StudentJson对象
	 */
	StudentJson findByIdCard(String idCard);
	
	/**
	 * 分页查询
	 * @param pageNumber   页码
	 * @param pageSize     分页大小
	 * @param sortOrder    排序
	 * @param searchText   查询字段
	 * @return  Map集合
	 */
	Map<String, Object> findStudentByPage(Integer pageNumber, Integer pageSize, String sortOrder, String searchText);

	/**
	 * 获取待审核的学生
	 * @param pageNumber   页码
	 * @param pageSize     分页大小
	 * @return  Map集合
	 */
	Map<String, Object> getNeedToVerify(Integer pageNumber, Integer pageSize);
}
