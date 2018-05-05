package org.jyu.web.dao.paper;

import org.jyu.web.entity.paper.Paper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepository extends JpaRepository<Paper, String> {
	
	Paper findByName(String name);

	/**
	 * 动态分页查询
	 * @param specification
	 * @param pageable
	 * @return
	 */
	Page<Paper> findAll(Specification<Paper> specification, Pageable pageable);
}
