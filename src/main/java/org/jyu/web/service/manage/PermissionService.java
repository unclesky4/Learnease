package org.jyu.web.service.manage;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.manage.PermissionJson;
import org.jyu.web.dto.manage.ZtreePermission;
import org.jyu.web.entity.manage.Permission;

public interface PermissionService {
	
	/**
	 * 通过主键查询
	 * @param id  权限主键
	 * @return   Permission对象
	 */
	Permission findById(String id);
	
	/**
	 * 查询所有权限
	 * @return   Map集合
	 */
	Map<String, Object> findAll();
	
	/**
	 * 批量删除
	 * @param ids  主键（逗号分割）
	 * @return   Result对象
	 */
	Result delete(List<String> ids);
	
	/**
	 * 保存权限
	 * @param name   权限名
	 * @param code    权限代码
	 * @param isCatalog  是否为目录
	 * @param status   启用状态
	 * @param pid   父权限
	 * @return  Result对象
	 */
	Result save(String name, String code, Boolean status, Boolean isCatalog, String pid);
	
	/**
	 * 修改权限
	 * @param id    权限主键
	 * @param name   权限名
	 * @param code    权限代码
	 * @param isCatalog  是否为目录
	 * @param status   启用状态
	 * @return  Result对象
	 */
	Result update(String id, String name, String code, Boolean isCatalog, Boolean status);
	
	/**
	 * 修改父权限
	 * @param id   主键
	 * @param targetId  父权限主键
	 * @return Result对象
	 */
	Result update(String id, String targetId);
	
	/**
	 * 获取所有权限数据，方便zTree插件处理数据
	 * @return  List集合
	 */
	 List<ZtreePermission> findForZTree();
	 
	 /**
	  * 获取已启用的权限
	  * @return  List集合
	  */
	 List<ZtreePermission> findValidPermission();
	 
	 /**
	  * 获取权限信息 - 针对管理界面侧边栏
	  * @return  Map集合
	  */
	 Map<String, List<PermissionJson>> findForSideBar();
	 
	 /**
	  * 批量更新启用状态
	  * @param upIds    须启用的权限主键（逗号分割）
	  * @param downIds  不启用的权限主键（逗号分割）
	  * @return  Result对象
	  */
	 Result updateStatus(String upIds, String downIds);
	 
}
