package org.jyu.web.dao.authority;

import org.jyu.web.entity.authority.Root;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RootRepository extends JpaRepository<Root, String>{
	
	Root findByNameOrEmail(String name, String email);
	
	Root findByNameAndPwd(String name, String pwd);
	
	Root findByEmailAndPwd(String email, String pwd);
	
}
