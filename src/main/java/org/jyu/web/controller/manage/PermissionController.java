package org.jyu.web.controller.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.Length;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.manage.PermissionJson;
import org.jyu.web.dto.manage.ZtreePermission;
import org.jyu.web.service.manage.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 保存权限
	 * @param name   权限名
	 * @param code   权限代码
	 * @param isCatalog   类型 true:目录  false:权限
	 * @param pid    父权限
	 * @return   Result对象
	 */
	@RequiresPermissions({"permission:add"})
	@RequestMapping(value="/permission/add", method=RequestMethod.POST)
	public Result save(@Length(min=1, max=20, message="权限名长度为1-20位")String name, 
			@RequestParam(value="code", required=false)String code,
			@RequestParam(value="isCatalog", required=true)Boolean isCatalog,
			@RequestParam(value="pid", required=false)String pid) {
		return permissionService.save(name, code, isCatalog, true, pid);
	}
	
	/**
	 * zTree插件获取所有权限数据
	 * @return  List集合
	 */
	@RequiresPermissions({"permission:query"})
	@RequestMapping(value="/permission/all", method=RequestMethod.GET)
	public List<ZtreePermission> getRootNodeForZtree(){
		List<ZtreePermission> list = permissionService.findForZTree();
		return list;
	}
	
	/**
	 * 获取已启用的权限
	 * @return  List集合
	 */
	@RequiresPermissions({"permission:query"})
	@GetMapping(value="/permission/valid")
	public List<ZtreePermission> getValidPermission() {
		return permissionService.findValidPermission();
	}
	
	/**
	 * 更新权限
	 * @param id    权限主键
	 * @param name   权限名称
	 * @param code   权限代码
	 * @param isCatalog   是否为目录
	 * @param status   启用状态
	 * @return   Result对象
	 */
	@RequiresPermissions({"permission:update"})
	@RequestMapping(value="/permission/update", method=RequestMethod.POST)
	public Result updatePermission(String id, String name, String code, Boolean isCatalog, Boolean status) {
		return permissionService.update(id, name, code, isCatalog, status);
	}
	
	/**
	 * 修改某个权限的父权限
	 * @param id   权限主键
	 * @param targetId   父权限主键
	 * @return    Result对象
	 */
	@RequiresPermissions({"permission:update"})
	@PostMapping(value="/permission/updatePid")
	public Result updatePid(String id, String targetId) {
		return permissionService.update(id, targetId);
	}
	
	/**
	 * 批量更新启用状态
	 * @param upIds  须启用的权限主键（逗号分割）
	 * @param downIds   须禁用的权限主键（逗号分割）
	 * @return   Result对象
	 */
	@RequiresPermissions({"permission:update"})
	@RequestMapping(value="/permission/status/update", method=RequestMethod.POST)
	public Result updateStatus(String upIds, String downIds) {
		return permissionService.updateStatus(upIds, downIds);
	}
	
	/**
	 * 删除权限
	 * @param id  主键
	 * @return   Result对象
	 */
	@RequiresPermissions({"permission:delete"})
	@RequestMapping(value="/permission/del", method=RequestMethod.POST)
	public Result delete(String id) {
		List<String> list = new ArrayList<>();
		list.add(id);
		return permissionService.delete(list);
	}
	
	/**
	 * 获取用于侧边栏的权限
	 * @return  Map集合
	 */
	@RequiresAuthentication
	@GetMapping(value="/permission/sidebar")
	Map<String, List<PermissionJson>> findForSideBar() {
		return permissionService.findForSideBar();
	}
}
