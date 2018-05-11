package org.jyu.web.controller.common;

import org.apache.shiro.SecurityUtils;
import org.jyu.web.dto.manage.AdministratorJson;
import org.jyu.web.entity.manage.Administrator;
import org.jyu.web.service.manage.AdministratorService;
import org.jyu.web.service.manage.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
/**
 * @ClassName: ManageViewController 
 * @Description: 管理界面模块的所有页面跳转
 * @author: unclesky4
 * @date: May 9, 2018 10:05:01 PM
 */
@RestController
public class ManageViewController {
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private AdministratorService adminService;
	
	/**
	 * 管理员登陆界面
	 * @param mv  ModelAndView
	 * @return  ModelAndView
	 */
	@GetMapping(value="/admin")
	public ModelAndView login_html(ModelAndView mv) {
		mv.setViewName("/manage/login.html");
		return mv;
	}
	
	/**
	 * 管理员信息页面
	 * @param mv  ModelAndView
	 * @return  ModelAndView
	 */
	@GetMapping(value="/admin_profile")
	public ModelAndView profile_html(ModelAndView mv) {
		mv.setViewName("/manage/profile.html");
		mv.addObject("permissions", permissionService.findForSideBar());
		//System.out.println(JsonUtil.toJson(permissionService.findForSideBar()));
		mv.addObject("adminName", SecurityUtils.getSubject().getSession().getAttribute("adminName"));
		
		String adminId = (String) SecurityUtils.getSubject().getSession().getAttribute("adminId");
		mv.addObject("adminId", adminId);
		return mv;
	}
	
	/**
	 * 权限管理页面
	 * @param mv  ModelAndView
	 * @return ModelAndView
	 */
	@RequestMapping(value="/permission_html", method=RequestMethod.GET)
	public ModelAndView permission_html(ModelAndView mv) {
		mv.setViewName("/manage/permission.html");
		mv.addObject("permissions", permissionService.findForSideBar());
		mv.addObject("adminName", SecurityUtils.getSubject().getSession().getAttribute("adminName"));
		return mv;
	}
	
	/**
	 * 角色管理界面
	 * @param mv  ModelAndView
	 * @return ModelAndView
	 */
	@GetMapping(value="/role_html")
	public ModelAndView role_html(ModelAndView mv) {
		mv.setViewName("/manage/role_manage.html");
		mv.addObject("permissions", permissionService.findForSideBar());
		mv.addObject("adminName", SecurityUtils.getSubject().getSession().getAttribute("adminName"));
		return mv;
	}
	
	/**
	 * 管理员管理界面
	 * @param mv  ModelAndView
	 * @return ModelAndView
	 */
	@GetMapping(value="/admin_html")
	public ModelAndView admin_manage(ModelAndView mv) {
		mv.setViewName("/manage/admin_manage.html");
		mv.addObject("permissions", permissionService.findForSideBar());
		mv.addObject("adminName", SecurityUtils.getSubject().getSession().getAttribute("adminName"));
		return mv;
	}
	
	/**
	 * 教师申请管理界面
	 * @param mv  ModelAndView
	 * @return ModelAndView
	 */
	@GetMapping(value="/teacher_application")
	public ModelAndView teacher_apply_html(ModelAndView mv) {
		mv.setViewName("/manage/teacher_apply_manage.html");
		mv.addObject("permissions", permissionService.findForSideBar());
		mv.addObject("adminName", SecurityUtils.getSubject().getSession().getAttribute("adminName"));
		return mv;
	}
	
	/**
	 * 学生申请管理界面
	 * @param mv  ModelAndView
	 * @return ModelAndView
	 */
	@GetMapping(value="/student_application")
	public ModelAndView student_apply_html(ModelAndView mv) {
		mv.setViewName("/manage/student_apply_manage.html");
		mv.addObject("permissions", permissionService.findForSideBar());
		mv.addObject("adminName", SecurityUtils.getSubject().getSession().getAttribute("adminName"));
		return mv;
	}
	
	
	/**
	 * 所有用户信息界面
	 * @param mv  ModelAndView
	 * @return ModelAndView
	 */
	@GetMapping(value="/user_html")
	public ModelAndView user_html(ModelAndView mv) {
		mv.setViewName("/manage/user_all.html");
		mv.addObject("permissions", permissionService.findForSideBar());
		mv.addObject("adminName", SecurityUtils.getSubject().getSession().getAttribute("adminName"));
		return mv;
	}
	
	/**
	 * 试题标签界面
	 * @param mv  ModelAndView
	 * @return ModelAndView
	 */
	@GetMapping(value="/questionlabel_html")
	public ModelAndView qustionlabel_html(ModelAndView mv) {
		mv.setViewName("/manage/questionlabel_manage.html");
		mv.addObject("permissions", permissionService.findForSideBar());
		mv.addObject("adminName", SecurityUtils.getSubject().getSession().getAttribute("adminName"));
		return mv;
	}
	
	/**
	 * 试卷标签界面
	 * @param mv  ModelAndView
	 * @return ModelAndView
	 */
	@GetMapping(value="/paperlabel_html")
	public ModelAndView paperlabel_html(ModelAndView mv) {
		mv.setViewName("/manage/paperlabel_manage.html");
		mv.addObject("permissions", permissionService.findForSideBar());
		mv.addObject("adminName", SecurityUtils.getSubject().getSession().getAttribute("adminName"));
		return mv;
	}

}
