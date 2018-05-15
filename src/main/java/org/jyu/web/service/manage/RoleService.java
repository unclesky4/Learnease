package org.jyu.web.service.manage;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.manage.RoleJson;
import org.jyu.web.dto.manage.ZtreePermission;
import org.jyu.web.entity.manage.Role;

public interface RoleService {

	/**
	 * 通过主键查询角色
	 * @param id   角色主键
	 * @return
	 */
	Role findById(String id);
	
	/**
	 * 分页查询角色
	 * @param pageNumber    页码
	 * @param pageSize    显示条数
	 * @return
	 */
	Map<String, Object> findAll(Integer pageNumber, Integer pageSize);
	
	/**
	 * 查询所有角色
	 * @return
	 */
	List<RoleJson> list();
	
	/**
	 * 保存角色
	 * @param name   角色名
	 * @param code   代码
	 * @param description   描述
	 * @return
	 */
	Result save(String name, String code, String description);
	
	/**
	 * 更新角色
	 * @param id    主键
	 * @param name   角色名
	 * @param code   代码
	 * @param description   描述
	 * @param permissionIds  权限主键（逗号分割）
	 * @return
	 */
	Result update(String id, String name, String code, String description, List<String> permissionIds);
	
	/**
	 * 通过主键删除角色
	 * @param id   角色主键
	 * @return
	 */
	Result deleteById(String id);
	
	/**
	 * 批量删除角色
	 * @param ids   主键（逗号分割）
	 * @return
	 */
	Result delete(List<String> ids);
	
	/**
	 * 获取某个角色有效的权限
	 * @param id   角色主键
	 * @return
	 */
	List<ZtreePermission> findRolePermissions(String id);
}
