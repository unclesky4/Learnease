package org.jyu.web.dao.authority;

import java.util.List;

import org.jyu.web.entity.authority.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	User findByEmail(String email);
	
	User findByCode(String code);
	
	List<User> findByEmailLike(String email);

	User findByEmailAndPwd(String email, String pwd);
	
	Page<User> findAll(Specification<User> specification, Pageable pageable);
	
	List<User> findAll(Specification<User> specification, Sort sort);
}
