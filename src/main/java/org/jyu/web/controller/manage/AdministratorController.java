package org.jyu.web.controller.manage;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.manage.AdministratorJson;
import org.jyu.web.entity.manage.Administrator;
import org.jyu.web.service.manage.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdministratorController {
	
	@Autowired
	private AdministratorService adminService;
	
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
	
	/**
	 * 更新信息
	 * @param id   主键
	 * @param name  姓名
	 * @param phone  电话
	 * @param sex  性别
	 * @param email  邮箱
	 * @param roldIds  角色主键（逗号分割）
	 * @return  Result
	 */
	@PostMapping(value="/admin/update")
	public Result update(String id, String name, String phone, String sex, String email, String roldIds) {
		return adminService.update(id, name, sex, phone, email, roldIds);
	}
	
	/**
	 * 修改密码
	 * @param id  主键
	 * @param oldPwd  旧密码
	 * @param newPwd  新密码
	 * @param newPwd_repeat  确认新密码
	 * @return
	 */
	@PostMapping(value="/admin/pwd/update")
	public Result updatePwd(String id, String oldPwd, String newPwd, String newPwd_repeat) {
		if (newPwd.equals("") || newPwd_repeat.equals("")) {
			return new Result(false, "新密码不能为空");
		}
		if (!newPwd_repeat.equals(newPwd)) {
			return new Result(false, "新密码不一致");
		}
		return adminService.updatePwd(id, oldPwd, newPwd_repeat);
	}
	
	/**
	 * 修改某个管理员的密码
	 * @param id  主键
	 * @param newPwd   新密码
	 * @return
	 */
	@PostMapping(value="/admin/password/update")
	public Result updatePwd(String id, String newPwd) {
		return adminService.updatePassword(id, newPwd);
	}
	
	/**
	 * 通过主键查询管理员
	 * @param id   管理员主键
	 * @return AdministratorJson
	 */
	@GetMapping(value="/admin/findById")
	public AdministratorJson findById(String id) {
		Administrator administrator = adminService.findById(id);
		
		AdministratorJson json = new AdministratorJson();
		if (administrator == null) {
			return json;
		}
		json.setCreateTime(administrator.getCreateTime());
		json.setEmail(administrator.getEmail());
		json.setId(administrator.getId());
		json.setName(administrator.getName());
		json.setPhone(administrator.getPhone());
		json.setSex(administrator.getSex());
		return json;
	}

}
