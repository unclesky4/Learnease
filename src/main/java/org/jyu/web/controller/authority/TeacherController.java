package org.jyu.web.controller.authority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.apache.shiro.SecurityUtils;
import org.hibernate.validator.constraints.Length;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.authority.TeacherJson;
import org.jyu.web.service.authority.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {
	
	@Autowired
	private TeacherService service;
	
	/**
	 * 申请教师角色
	 * @param name  姓名
	 * @param sex  性别
	 * @param idCard   职工号
	 * @param subject  学科
	 * @param phone  手机电话
	 * @return
	 */
	@RequestMapping(value="/teacher/save", method=RequestMethod.POST)
	public Result saveTeacher(@Length(min=1, max=20, message="姓名长度为1-20位")String name, 
			@NotBlank(message="性别不能为空")String sex, 
			@Length(min=1, max=20, message="职工号长度为1-10位")String idCard, 
			@Length(min=1, max=20, message="学科长度为1-30位")String subject, 
			@Pattern(regexp="^1[3|4|5|7|8][0-9]{9}$", message="请输入正确的手机格式")String phone) {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		if (userId == null) {
			return new Result(false, "请登陆");
		}
		return service.save(name, sex, idCard, subject, phone, userId);
	}

	/**
	 * 删除教师
	 * @param tid
	 * @return
	 */
	@RequestMapping(value="/teacher/remove", method=RequestMethod.POST)
	public Result removeTeacher(String tid) {
		List<String> ids = new ArrayList<String>();
		ids.add(tid);
		return service.delete(ids);
	}
	
	/**
	 * 通过主键查询教师信息
	 * @param tid    主键
	 * @return
	 */
	@RequestMapping(value="/teacher/findTeacherById", method=RequestMethod.GET)
	public TeacherJson findTeacherById(String tid) {
		return service.findById(tid);
	}
	
	/**
	 * 通过职工号查询教师
	 * @param idCard
	 * @return
	 */
	@RequestMapping(value="/teacher/findTeacherByIdCard", method=RequestMethod.GET)
	public TeacherJson findTeacherByIdCard(String idCard) {
		return service.findByIdCard(idCard);
	}
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @param search
	 * @return
	 */
	@RequestMapping(value="/teacher/page_json", method=RequestMethod.GET)
	public Map<String, Object> getAllTeacher(int pageNumber, int pageSize, String sortOrder, String search) {
		return service.findTeacherByPage(pageNumber, pageSize, sortOrder, search);
	}
	
	/**
	 * 修改教师信息
	 * @param tid    主键
	 * @param name  姓名
	 * @param sex  性别
	 * @param idCard   职工号
	 * @param subject  学科
	 * @param phone  手机电话
	 * @return
	 */
	@RequestMapping(value="/teacher/update", method=RequestMethod.POST)
	public Result updateTeacher(String tid, @Length(min=1, max=20, message="姓名长度为1-20位")String name, 
			@NotBlank(message="性别不能为空")String sex, 
			@Length(min=1, max=20, message="职工号长度为1-10位")String idCard, 
			@Length(min=1, max=20, message="学科长度为1-30位")String subject, 
			@Pattern(regexp="^1[3|4|5|7|8][0-9]{9}$", message="请输入正确的手机格式")String phone) {
		return service.update(tid, name, sex, idCard, subject, phone, null);
	}
	
	/**
	 * 修改教师审核状态
	 * @param id   主键
	 * @param status   审核状态
	 * @return
	 */
	@PostMapping(value="/teacher/status/update")
	public Result updateStatus(String id, Integer status) {
		return service.update(id, null, null, null, null, null, status);
	}
	
	/**
	 * 删除教师
	 * @param id 主键
	 * @return
	 */
	@PostMapping(value="/teacher/delete")
	public Result deleteById(String id) {
		return service.deleteById(id);
	}
	
	/**
	 * 查询待审核的教师
	 * @return
	 */
	@GetMapping(value="/teacher/verification")
	public Map<String, Object> geTeacherByVerification(Integer pageNumber, Integer pageSize) {
		return service.getNeedToVerify(pageNumber, pageSize);
	}
	
}
