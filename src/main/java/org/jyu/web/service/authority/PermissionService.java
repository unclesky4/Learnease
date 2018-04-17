package org.jyu.web.service.authority;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.ZtreeJson;
import org.jyu.web.entity.authority.Permission;

public interface PermissionService {
	
	Permission findById(String id);
	
	Map<String, Object> findAll();
	
	Result delete(List<String> ids);
	
	Result save(String name, String code, Boolean isRoot, Boolean isCatalog, Boolean isMenuShow, 
			String pid);
	
	Result update(String id, String name, String code, Boolean isRoot, Boolean isCatalog, Boolean isMenuShow);
	
	/**
	 * 
	 * @Description: 获取数据，方便zTree插件处理数据
	 * @return: List<ZtreeJson>
	 * @throws
	 *
	 */
	 List<ZtreeJson> findForZTree(String id);

}
