package org.jyu.web.controller.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.manage.RoleJson;
import org.jyu.web.dto.manage.ZtreePermission;
import org.jyu.web.entity.manage.Permission;
import org.jyu.web.entity.manage.Role;
import org.jyu.web.service.manage.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 保存
	 * @param name   角色名
	 * @param code   角色代码
	 * @param description 描述
	 * @return  Result
	 */
	@PostMapping(value="/role/add")
	public Result save(String name, String code, String description) {
		return roleService.save(name, code, description);
	}
	
	/**
	 * 更新角色信息
	 * @param id   主键
	 * @param name  角色名
	 * @param code   代码
	 * @param description 描述
	 * @return
	 */
	@PostMapping(value="/role/update")
	public Result update(String id, String name, String code, String description) {
		return roleService.update(id, name, code, description, null);
	}
	
	/**
	 * 更新角色权限
	 * @param id     角色主键
	 * @param permissionIds   权限主键（逗号分割）
	 * @return
	 */
	@PostMapping(value="/role/update/permission")
	public Result update(String id, String permissionIds) {
		String[] permissionId = permissionIds.split(",");
		
		List<String> list = new ArrayList<>();
		int size = permissionId.length;
		for (int i = 0; i < size; i++) {
			if (!permissionId[i].equals("")) {
				list.add(permissionId[i]);
			}
		}
		return roleService.update(id, null, null, null, list);
	}
	
	/**
	 * 删除角色
	 * @param id   主键
	 * @return
	 */
	@PostMapping(value="/role/delete")
	public Result deleteById(String id) {
		return roleService.deleteById(id);
	}
	
	/**
	 * 分页查询
	 * @param pageNumber  页码
	 * @param pageSize    显示条数
	 * @return
	 */
	@GetMapping(value="/role/page_json")
	public Map<String, Object> getAll(Integer pageNumber, Integer pageSize) {
		return roleService.findAll(pageNumber, pageSize);
	}

	/**
	 * 获取某个角色的所有权限
	 * @param id   角色主键
	 * @return
	 */
	@GetMapping(value="/role/permissions")
	public List<ZtreePermission> getRolePermissions(String id) {
		return roleService.findRolePermissions(id);
	}
	
	/**
	 * 获取所有角色信息
	 * @return
	 */
	@GetMapping(value="/role/all")
	public List<RoleJson> all() {
		return roleService.list();
	}
}
