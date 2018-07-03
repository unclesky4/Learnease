package org.jyu.web.controller.authority;

import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.shiro.SecurityUtils;
import org.hibernate.validator.constraints.Length;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.authority.StudentJson;
import org.jyu.web.service.authority.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: StudentController 
 * @Description: 学生管理
 * @author: unclesky4
 * @date: Jul 3, 2018 6:46:57 PM
 */
@RestController
public class StudentController {
	
	@Autowired
	private StudentService service;
	
	/**
	 * 申请学生角色
	 * @param idCard   学号
	 * @param stuName   姓名
	 * @param stuSex   性别
	 * @param stuAcademy   学院
	 * @param stuClass   班级
	 * @param stuEntranceTime   入学年份
	 * @return    Result对象
	 */
	@RequestMapping(value="/student/save", method=RequestMethod.POST)
	public Result saveStudent(@Length(min=1, max=10, message="学号长度为1-10位")String idCard, 
			@Length(min=1, max=10, message="姓名长度为1-20位")String stuName,
			@NotBlank(message="性别不能为空")String stuSex,
			@Length(min=1, max=10, message="学院长度为1-100位")String stuAcademy,
			@Length(min=1, max=10, message="班级长度为1-10位")String stuClass,
			@Size(min=2000, max=3000, message="请正确输入入学年份")Integer stuEntranceTime){
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		if (userId  == null) {
			return new Result(false, "请登陆");
		}
		return service.save(idCard, stuName, stuSex, stuAcademy, stuClass, stuEntranceTime, userId);
	}
	
	/**
	 * 通过学号查询学生信息
	 * @param idCard   学号
	 * @return  StudentJson对象
	 */
	@RequestMapping(value="/student/findStudentByIdCard", method=RequestMethod.GET)
	public StudentJson findStudentByIdCard(String idCard) {
		return service.findByIdCard(idCard);
	}
	
	/**
	 * 通过主键查找学生信息
	 * @param stuId   主键
	 * @return   StudentJson对象
	 */
	@RequestMapping(value="/student/findStudentById", method=RequestMethod.GET)
	public StudentJson findStudentById(String stuId) {
		return service.findById(stuId);
	}

	/**
	 * 分页查询
	 * @param pageNumber   页码
	 * @param pageSize   每页显示条数
	 * @param sortOrder   排序
	 * @param search   查询字段
	 * @return   Map集合
	 */
	@RequestMapping(value="/student/page_json", method=RequestMethod.GET)
	public Map<String, Object> getAllStudent(Integer pageNumber, Integer pageSize, String sortOrder, String search) {
		return service.findStudentByPage(pageNumber, pageSize, sortOrder, search);
	}
	
	/**
	 * 修改学生信息
	 * @param stuId  主键
	 * @param idCard  学号
	 * @param stuName  姓名
	 * @param stuSex   性别
	 * @param academy   学院
	 * @param stuClass   班级
	 * @param stuEntranceTime   入学年份
	 * @return   Result对象
	 */
	@RequestMapping(value="/student/updateStudent", method=RequestMethod.POST)
	public Result updateStudent(String stuId, String idCard,String stuName,String stuSex,String academy,String stuClass,
			Integer stuEntranceTime) {
		return service.update(stuId, idCard, stuName, stuSex, academy, stuClass, stuEntranceTime, null);
	}
	
	/**
	 * 修改学生角色状态
	 * @param stuId   学生角色主键
	 * @param status    状态
	 * @return   Result对象
	 */
	@RequestMapping(value="/student/status/update", method=RequestMethod.POST)
	public Result updateStudentStatus(String stuId, Integer status) {
		StudentJson json = service.findById(stuId);
		if (json == null) {
			return new Result(false, "对象不存在");
		}
		return service.update(stuId, null, null, null, null, null, null, status);
	}
	
	/**
	 * 删除学生
	 * @param id   主键
	 * @return   Result对象
	 */
	@PostMapping(value="/student/delete")
	public Result delete(String id) {
		return service.delete(id);
	}

	/**
	 * 获取待审核的学生信息
	 * @param pageNumber    页码
	 * @param pageSize      分页大小
	 * @return   Map集合
	 */
	@GetMapping(value="/student/verification")
	public Map<String, Object> geStudentByVerification(Integer pageNumber, Integer pageSize) {
		return service.getNeedToVerify(pageNumber, pageSize);
	}
}
