package org.jyu.web.dao.authority;

import java.util.List;

import org.jyu.web.entity.authority.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
	
	/**
	 * 通过工号查找教师信息
	 * @param idCard  工号
	 * @return
	 */
	Teacher findByIdCard(String idCard);
	
	/**
	 * 条件查询
	 * @param specification
	 * @param pageable
	 * @return
	 */
	Page<Teacher> findAll(Specification<Teacher> specification, Pageable pageable);
	
	/**
	 * 条件查询
	 * @param specification  Specification<Teacher>
	 * @return
	 */
	Page<Teacher> findAll(Specification<Teacher> specification);
	
	/**
	 * 通过审核状态查询
	 * @param status  审核状态
	 * @return
	 */
	List<Teacher> findByStatus(Integer status);
}
