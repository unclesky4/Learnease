package org.jyu.web.dao.authority;

import org.jyu.web.entity.authority.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
	
	//通过工号查找教师信息
	Teacher findByIdCard(String idCard);
	
	Page<Teacher> findAll(Specification<Teacher> specification, Pageable pageable);
}
