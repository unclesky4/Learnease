package org.jyu.web.controller.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.hibernate.sql.Delete;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.ZtreeJson;
import org.jyu.web.dto.manage.AdministratorJson;
import org.jyu.web.dto.manage.RoleJson;
import org.jyu.web.entity.manage.Administrator;
import org.jyu.web.entity.manage.Role;
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
	
	
	/*--------------超级管理员权限------------------*/
	
	/**
	 * 保存管理员
	 * @param name    姓名
	 * @param sex     性别
	 * @param phone   电话
	 * @param email   邮箱
	 * @param pwd     密码
	 * @param repeatPwd   确认密码
	 * @return
	 */
	@PostMapping(value="/admin/add")
	public Result save(String name, String sex, String phone, String email, String pwd, String repeatPwd) {
		if (!pwd.equals(repeatPwd)) {
			return new Result(false, "两次密码不一致");
		}
		return adminService.save(name, pwd, sex, phone, email, null);
	}
	
	/**
	 * 指派角色
	 * @param id     管理员主键
	 * @param roleIds   角色主键（逗号分割）
	 * @return
	 */
	@PostMapping(value="/admin/role/update")
	public Result updateRole(String id, String roleIds) {
		if (roleIds == null || roleIds == "") {
			return new Result(false, "请至少选择一个角色");
		}
		Result result = adminService.update(id, null, null, null, null, roleIds);
		if (result.getSuccess()) {
			result.setMsg("操作成功");
		}else {
			result.setMsg("操作失败");
		}
		return result;
	}
	
	/**
	 * 分页查询
	 * @param pageNumber   页码
	 * @param pageSize   每页显示条数
	 * @return
	 */
	@GetMapping(value="/admin/page_json")
	public Map<String, Object> pageJson(Integer pageNumber, Integer pageSize) {
		return adminService.finAll(pageNumber, pageSize);
	}
	
	/**
	 * 修改某个管理员的密码
	 * @param id  主键
	 * @param newPwd   新密码
	 * @param repeatPwd   确认密码
	 * @return
	 */
	@PostMapping(value="/admin/password/update")
	public Result updatePwd(String id, String newPwd, String repeatPwd) {
		if (!newPwd.equals(repeatPwd)) {
			return new Result(false, "两次密码不一致");
		}
		return adminService.updatePassword(id, newPwd);
	}
	
	/**
	 * 删除管理员
	 * @param id    主键
	 * @return
	 */
	@PostMapping(value="/admin/delete")
	public Result delete(String id) {
		return adminService.deleteById(id);
	}
	
	/**
	 * 获取某个管理员关联的角色
	 * @param id   管理员主键
	 * @return
	 */
	@GetMapping(value="/admin/roles")
	public List<RoleJson> findRoleByAdmin(String id) {
		List<RoleJson> list = new ArrayList<>();
		
		Administrator administrator = adminService.findById(id);
		if (administrator == null || administrator.getRoles() == null) {
			return list;
		}
		List<Role> roles = administrator.getRoles();
		for (Role role : roles) {
			RoleJson json = new RoleJson();
			json.setCode(role.getCode());
			json.setId(role.getId());
			json.setName(role.getName());
			list.add(json);
		}
		return list;
	}

}
