package org.jyu.web.dao.authority;

import org.jyu.web.entity.authority.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>{

	Student findByIdCard(String idCard);
	
	Page<Student> findAll(Specification<Student> specification, Pageable pageable);
}
