package org.jyu.web.dao.authority;

import java.util.List;

import org.jyu.web.entity.authority.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>{

	/**
	 * 通过学号查询
	 * @param idCard   学号
	 * @return
	 */
	Student findByIdCard(String idCard);
	
	/**
	 * 条件查询
	 * @param specification  Specification<Student>
	 * @param pageable   Pageable
	 * @return
	 */
	Page<Student> findAll(Specification<Student> specification, Pageable pageable);
	
	/**
	 * 条件查询
	 * @param specification  Specification<Student>
	 * @return
	 */
	Page<Student> findAll(Specification<Student> specification);
	
	/**
	 * 通过审核状态查询
	 * @param status   审核状态
	 * @return
	 */
	List<Student> findByStatus(Integer status);
}
