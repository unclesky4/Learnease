package org.jyu.web.controller.authority;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.authority.StudentJson;
import org.jyu.web.service.authority.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
	
	@Autowired
	private StudentService service;
	
	/**
	 * 申请学生角色
	 * @param idCard
	 * @param stuName
	 * @param stuSex
	 * @param stuAcademy
	 * @param stuClass
	 * @param stuEntranceTime
	 * @return
	 */
	@RequestMapping(value="/student/save", method=RequestMethod.POST)
	public Result saveStudent(String idCard,String stuName,String stuSex,String stuAcademy,String stuClass,
			Integer stuEntranceTime){
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		if (userId  == null) {
			return new Result(false, "请登陆");
		}
		return service.save(idCard, stuName, stuSex, stuAcademy, stuClass, stuEntranceTime, userId);
	}
	
	/**
	 * 通过学号查询学生信息
	 * @param idCard
	 * @return
	 */
	@RequestMapping(value="/student/findStudentByIdCard", method=RequestMethod.GET)
	public StudentJson findStudentByIdCard(String idCard) {
		return service.findByIdCard(idCard);
	}
	
	/**
	 * 通过主键查找学生信息
	 * @param stuId
	 * @return
	 */
	@RequestMapping(value="/student/findStudentById", method=RequestMethod.GET)
	public StudentJson findStudentById(String stuId) {
		return service.findById(stuId);
	}

	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @param searchText
	 * @return
	 */
	@RequestMapping(value="/student/getAllStudent", method=RequestMethod.GET)
	public Map<String, Object> getAllStudent(Integer pageNumber, Integer pageSize, String sortOrder, String searchText) {
		return service.findStudentByPage(pageNumber, pageSize, sortOrder, searchText);
	}
	
	/**
	 * 修改学生信息
	 * @param stuId
	 * @param idCard
	 * @param stuName
	 * @param stuSex
	 * @param academyId
	 * @param stuClass
	 * @param stuEntranceTime
	 * @return
	 */
	@RequestMapping(value="/student/updateStudent", method=RequestMethod.POST)
	public Result updateStudent(String stuId, String idCard,String stuName,String stuSex,String academyId,String stuClass,
			Integer stuEntranceTime) {
		return service.update(stuId, idCard, stuName, stuSex, academyId, stuClass, stuEntranceTime, null);
	}
	
	/**
	 * 修改学生角色状态
	 * @param stuId
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/student/updateStudentStatus", method=RequestMethod.POST)
	public Result updateStudentStatus(String stuId, Integer status) {
		StudentJson json = service.findById(stuId);
		if (json == null) {
			return new Result(false, "对象不存在");
		}
		return service.update(stuId, null, null, null, null, null, null, status);
	}
}
