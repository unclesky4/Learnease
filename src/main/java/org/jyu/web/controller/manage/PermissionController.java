package org.jyu.web.controller.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.ZtreeJson;
import org.jyu.web.dto.manage.PermissionJson;
import org.jyu.web.service.manage.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 保存权限
	 * @param name
	 * @param code
	 * @param isCatalog
	 * @param isMenuShow
	 * @param pid
	 * @return
	 */
	//@RequiresPermissions({"permission:add"})
	@RequestMapping(value="/permission_add", method=RequestMethod.POST)
	public Result save(@RequestParam(value="name", required=true)String name, 
			@RequestParam(value="code", required=true)String code,
			@RequestParam(value="isCatalog", required=true)Boolean isCatalog, 
			@RequestParam(value="isMenuShow", required=true)Boolean isMenuShow,
			@RequestParam(value="pid", required=true)String pid) {
		
		if(!isCatalog && isMenuShow) {
			return new Result(false, "只有目录才可以显示在侧边栏");
		}
		
		return permissionService.save(name, code, false, isCatalog, isMenuShow, pid);
	}
	
	/**
	 * 保存根节点权限
	 * @param name
	 * @param code
	 * @param isMenuShow
	 * @return
	 */
	@RequestMapping(value="/permission_root_add", method=RequestMethod.POST)
	public Result save(@RequestParam(value="name", required=true)String name, 
			@RequestParam(value="code", required=true)String code,
			@RequestParam(value="isMenuShow", required=true)Boolean isMenuShow) {
		return permissionService.save(name, code, true, true, isMenuShow, null);
	}
	
	/**
	 * zTree插件获取权限数据
	 * @param id  权限主键（null表示根节点）
	 * @return
	 */
	@RequestMapping(value="/ztree_data", method=RequestMethod.GET)
	public List<ZtreeJson> getRootNodeForZtree(@RequestParam(value="id", required=false)String id){
		List<ZtreeJson> list = permissionService.findForZTree(id);
		return list;
	}
	
	/**
	 * 更新权限
	 * @param id
	 * @param name
	 * @param code
	 * @param isCatalog
	 * @param isMenuShow
	 * @return
	 */
	@RequestMapping(value="/permission_up", method=RequestMethod.POST)
	public Result updatePermission(String id, String name,String code,Boolean isCatalog, Boolean isMenuShow) {
		return permissionService.update(id, name, code, null, isCatalog, isMenuShow);
	}
	
	/**
	 * 删除权限
	 * @param id  主键
	 * @return
	 */
	@RequestMapping(value="/permission_del", method=RequestMethod.POST)
	public Result delete(String id) {
		List<String> list = new ArrayList<>();
		list.add(id);
		return permissionService.delete(list);
	}
	
	/**
	 * 查询侧边栏权限
	 * @return
	 */
	@GetMapping(value="/permission/sidebar")
	Map<String, List<PermissionJson>> findForSideBar() {
		return permissionService.findForSideBar();
	}
}
