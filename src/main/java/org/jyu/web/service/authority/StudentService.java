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
	 * @return
	 */
	Result save(String idCard,String stuName,String stuSex,String stuAcademy,String stuClass,
			Integer stuEntranceTime,String userId);
	
	Result update(String stuId, String idCard,String stuName,String stuSex,String stuAcademy,String stuClass,
			Integer stuEntranceTime,Integer status);
	
	Result delete(List<String> ids);
	
	StudentJson findById(String id);
	
	/**
	 * 
	 * @Description: 通过学号查找学生信息
	 * @param: @param idCard
	 * @return: StudentJson
	 * @throws
	 *
	 */
	StudentJson findByIdCard(String idCard);
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @param searchText
	 * @return
	 */
	Map<String, Object> findStudentByPage(Integer pageNumber, Integer pageSize, String sortOrder, String searchText);
}
