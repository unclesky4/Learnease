package org.jyu.web.dao.manage;

import org.jyu.web.entity.manage.Administrator;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, String> {

	Administrator findByName(String name);
	
	Page<Administrator> findAll(Specification<Administrator> specification);
}
