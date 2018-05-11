package org.jyu.web.service.manage;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.ZtreeJson;
import org.jyu.web.dto.manage.PermissionJson;
import org.jyu.web.entity.manage.Permission;

public interface PermissionService {
	
	Permission findById(String id);
	
	Map<String, Object> findAll();
	
	Result delete(List<String> ids);
	
	/**
	 * 保存权限
	 * @param name   权限名
	 * @param code    权限代码
	 * @param isCatalog  是否为目录
	 * @param status   启用状态
	 * @param pid   父权限
	 * @return
	 */
	Result save(String name, String code, Boolean status, Boolean isCatalog, String pid);
	
	/**
	 * 
	 * @param id    权限主键
	 * @param name   权限名
	 * @param code    权限代码
	 * @param isCatalog  是否为目录
	 * @param status   启用状态
	 * @return
	 */
	Result update(String id, String name, String code, Boolean isCatalog, Boolean status);
	
	/**
	 * 修改父权限
	 * @param id   主键
	 * @param targetId  父权限主键
	 * @return
	 */
	Result update(String id, String targetId);
	
	/**
	 * 
	 * @Description: 获取所有权限数据，方便zTree插件处理数据
	 * @return: List<ZtreeJson>
	 * @throws
	 *
	 */
	 List<ZtreeJson> findForZTree();
	 
	 /**
	  * 获取权限信息 - 针对管理界面侧边栏
	  * @return
	  */
	 Map<String, List<PermissionJson>> findForSideBar();
	 
	 /**
	  * 批量更新启用状态
	  * @param upIds    须启用的权限主键（逗号分割）
	  * @param downIds  不启用的权限主键（逗号分割）
	  * @return
	  */
	 Result updateStatus(String upIds, String downIds);
	 
}
