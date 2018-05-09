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
	 * @param isRoot    是否为根节点
	 * @param isCatalog   是否为目录
	 * @param isMenuShow  是否显示在侧边栏
	 * @param pid   父权限
	 * @return
	 */
	Result save(String name, String code, Boolean isRoot, Boolean isCatalog, Boolean isMenuShow, 
			String pid);
	
	/**
	 * 
	 * @param id    权限主键
	 * @param name   权限名
	 * @param code    权限代码
	 * @param isRoot    是否为根节点
	 * @param isCatalog   是否为目录
	 * @param isMenuShow  是否显示在侧边栏
	 * @param pid   父权限
	 * @return
	 */
	Result update(String id, String name, String code, Boolean isRoot, Boolean isCatalog, Boolean isMenuShow);
	
	/**
	 * 
	 * @Description: 获取数据，方便zTree插件处理数据
	 * @return: List<ZtreeJson>
	 * @throws
	 *
	 */
	 List<ZtreeJson> findForZTree(String id);
	 
	 Map<String, List<PermissionJson>> findForSideBar();

}
