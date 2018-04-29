package org.jyu.web.dao.question;

import java.util.List;

import org.jyu.web.entity.question.QuestionSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionSimpleRepository extends JpaRepository<QuestionSimple, String> {
	
	List<QuestionSimple> findByShortName(String shortName);
	
	List<QuestionSimple> findAll(Specification<QuestionSimple> specification, Sort sort);
	
	Page<QuestionSimple> findAll(Specification<QuestionSimple> specification, Pageable pageable);
}
