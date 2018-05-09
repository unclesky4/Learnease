package org.jyu.web.controller.common;

import org.apache.shiro.SecurityUtils;
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
	 * @param mv
	 * @return
	 */
	@GetMapping(value="/admin")
	public ModelAndView login_html(ModelAndView mv) {
		mv.setViewName("/manage/login.html");
		return mv;
	}
	
	/**
	 * 管理员信息页面
	 * @param mv
	 * @return
	 */
	@GetMapping(value="/admin_profile")
	public ModelAndView profile_html(ModelAndView mv) {
		mv.setViewName("/manage/profile.html");
		mv.addObject("permissions", permissionService.findForSideBar());
		//System.out.println(JsonUtil.toJson(permissionService.findForSideBar()));
		mv.addObject("adminName", SecurityUtils.getSubject().getSession().getAttribute("adminName"));
		return mv;
	}
	
	/**
	 * 权限页面
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/permission_html", method=RequestMethod.GET)
	public ModelAndView toPermissionPage(ModelAndView mv) {
		mv.setViewName("/manage/permission.html");
		mv.addObject("permissions", permissionService.findForSideBar());
		mv.addObject("adminName", SecurityUtils.getSubject().getSession().getAttribute("adminName"));
		return mv;
	}

}
