package org.jyu.web.controller.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.Length;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.manage.RoleJson;
import org.jyu.web.dto.manage.ZtreePermission;
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
	 * @return  Result对象
	 */
	@RequiresPermissions({"role:add"})
	@PostMapping(value="/role/add")
	public Result save(@Length(min=1, max=20, message="角色名长度为1-20位")String name, 
			@Length(min=1, max=20, message="角色代码长度为1-20位")String code, String description) {
		return roleService.save(name, code, description);
	}
	
	/**
	 * 更新角色信息
	 * @param id   主键
	 * @param name  角色名
	 * @param code   代码
	 * @param description 描述
	 * @return   Result对象
	 */
	@RequiresPermissions({"role:update"})
	@PostMapping(value="/role/update")
	public Result update(String id, String name, String code, String description) {
		return roleService.update(id, name, code, description, null);
	}
	
	/**
	 * 更新角色权限
	 * @param id     角色主键
	 * @param permissionIds   权限主键（逗号分割）
	 * @return  Result对象
	 */
	@RequiresPermissions({"role:update"})
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
	 * @return  Result对象
	 */
	@RequiresPermissions({"role:delete"})
	@PostMapping(value="/role/delete")
	public Result deleteById(String id) {
		return roleService.deleteById(id);
	}
	
	/**
	 * 分页查询
	 * @param pageNumber  页码
	 * @param pageSize    显示条数
	 * @return  Map集合
	 */
	@RequiresPermissions({"role:query"})
	@GetMapping(value="/role/page_json")
	public Map<String, Object> getAll(Integer pageNumber, Integer pageSize) {
		return roleService.findAll(pageNumber, pageSize);
	}

	/**
	 * 获取某个角色的所有权限
	 * @param id   角色主键
	 * @return  List集合
	 */
	@RequiresPermissions({"role:query"})
	@GetMapping(value="/role/permissions")
	public List<ZtreePermission> getRolePermissions(String id) {
		return roleService.findRolePermissions(id);
	}
	
	/**
	 * 获取所有角色信息
	 * @return    List集合
	 */
	@RequiresPermissions({"role:query"})
	@GetMapping(value="/role/all")
	public List<RoleJson> all() {
		return roleService.list();
	}
}
