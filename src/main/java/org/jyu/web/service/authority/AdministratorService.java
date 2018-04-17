package org.jyu.web.service.authority;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.entity.authority.Administrator;

public interface AdministratorService {
	
	Result save(Administrator administrator);
	
	Result update(Administrator administrator, Boolean updatePassword);
	
	Result deleteById(String id);
	
	Result delete(List<String> ids);
	
	Administrator findById(String id);
	
	Administrator findByNameOrEmail(String name, String email);
	
	Administrator findByNameAndPwd(String name, String pwd);
	
	Administrator findByEmailAndPwd(String email, String pwd);
	
	Map<String, Object> finAll();

}
