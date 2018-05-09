package org.jyu.web.service.manage;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.entity.manage.Administrator;

public interface AdministratorService {
	
	Result save(String name, String pwd, String sex, String phone, String email, String roleIds);
	
	Result update(String id, String name, String pwd, String sex, String phone, String email, String roleIds, Boolean updatePassword);
	
	Result deleteById(String id);
	
	Result delete(List<String> ids);
	
	Administrator findById(String id);
	
	Administrator findByName(String name);
	
	Map<String, Object> finAll();

}
