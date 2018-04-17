package org.jyu.web.dao.authority;

import org.jyu.web.entity.authority.Administrator;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, String> {

	Administrator findByName(String name);
	
	Administrator findByNameAndPwd(String name, String pwd);
	
	Administrator findByNameOrEmail(String name, String email);
	
	Administrator findByEmailAndPwd(String email, String pwd);
	
	Page<Administrator> findAll(Specification<Administrator> specification);
}
