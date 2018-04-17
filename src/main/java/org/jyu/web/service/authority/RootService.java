package org.jyu.web.service.authority;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.entity.authority.Root;

public interface RootService {

	Result save(Root root);
	
	Result update(Root root, Boolean updatePassword);
	
	Result deleteById(String id);
	
	Result delete(List<String> ids);
	
	Root findById(String id);
	
	Root findByNameOrEmail(String name, String email);
	
	Root findByNameAndPwd(String name, String pwd);
	
	Root findByEmailAndPwd(String email, String pwd);
	
	Map<String, Object> finAll();
}
