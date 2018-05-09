package org.jyu.web.controller.manage;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jyu.web.dto.Result;
import org.jyu.web.entity.manage.Administrator;
import org.jyu.web.service.manage.AdministratorService;
import org.jyu.web.service.manage.PermissionService;
import org.jyu.web.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AdministratorController {
	
	@Autowired
	private AdministratorService adminService;
	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 管理员登陆
	 * @param name
	 * @param password
	 * @return
	 */
	@PostMapping(value="/admin/login")
	public Result login(String name, String password, @RequestParam(required=false, defaultValue="false")Boolean rememberMe) {
		UsernamePasswordToken token = new UsernamePasswordToken(name, password);  
	    token.setRememberMe(rememberMe);
	    Subject subject = SecurityUtils.getSubject();
	    subject.login(token);
	    
	    Administrator administrator = adminService.findByName(name);
	    subject.getSession().setAttribute("adminId", administrator.getId());
	    subject.getSession().setAttribute("adminName", administrator.getName());
	    subject.getSession().setTimeout(3*24*60*60*1000);
	    return new Result(true, "登陆成功");
	}

}
