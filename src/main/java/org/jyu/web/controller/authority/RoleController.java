package org.jyu.web.controller.authority;


import java.util.List;

import org.jyu.web.dto.Result;
import org.jyu.web.entity.manage.Role;
import org.jyu.web.service.manage.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 添加角色
	 * @param name  角色名
	 * @param code 角色代码
	 * @return
	 */
	//@RequiresPermissions({"role:add"})
	@RequestMapping(value="/role_add", method=RequestMethod.POST)
	public Result save(String name, String code) {
		return roleService.save(name, code);
	}
	
	
	//@RequiresPermissions({"role:query"})
	@RequestMapping(value="/role_list", method=RequestMethod.GET)
	public List<Role> list() {
		return roleService.list();
	}
	
	//@RequiresPermissions({"role:query"})
	@RequestMapping(value="/role_query", method=RequestMethod.GET)
	public Role getById(String id) {
		return roleService.findById(id);
	}
	
	//@RequiresPermissions({"role:del"})
	@RequestMapping(value="/role_del", method=RequestMethod.POST)
	public Result deleteById(String id) {
		return roleService.deleteById(id);
	}

}
