package org.jyu.web.controller.authority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.authority.TeacherJson;
import org.jyu.web.service.authority.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {
	
	@Autowired
	private TeacherService service;
	
	/**
	 * 申请教师角色
	 * @return
	 */
	@RequestMapping(value="/teacher/save", method=RequestMethod.POST)
	public Result saveTeacher(String name, String sex, String idCard, String subject, String phone) {
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
	 * @param tid
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
	 * 获取所有教师信息
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @param searchText
	 * @return
	 */
	@RequestMapping(value="/teacher/getAllTeacher", method=RequestMethod.GET)
	public Map<String, Object> getAllTeacher(int pageNumber, int pageSize, String sortOrder, String searchText) {
		return service.findTeacherByPage(pageNumber, pageSize, sortOrder, searchText);
	}
	
	/**
	 * 修改教师信息
	 * @return
	 */
	@RequestMapping(value="/teacher/update", method=RequestMethod.POST)
	public Result updateTeacher(String tid, String name, String sex, String idCard, String subject, String phone) {
		return service.update(tid, name, sex, idCard, subject, phone, null);
	}
	
}
