package org.jyu.web.dao.manage;

import org.jyu.web.entity.manage.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String>{
	
	Page<Role> findAll(Pageable pageable);
	
	Role findByCode(String code);

}
