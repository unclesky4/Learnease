package org.jyu.web.service.authority;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.entity.authority.Role;

public interface RoleService {

	Role findById(String id);
	
	Map<String, Object> findAll();
	
	List<Role> list();
	
	Result save(String name, String code);
	
	Result update(String id, String name, String code, List<String> permissionIds);
	
	Result deleteById(String id);
	
	Result delete(List<String> ids);
}
