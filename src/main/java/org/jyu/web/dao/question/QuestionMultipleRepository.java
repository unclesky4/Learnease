package org.jyu.web.dao.question;

import java.util.List;

import org.jyu.web.entity.question.QuestionMultiple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionMultipleRepository extends JpaRepository<QuestionMultiple, String> {
	
	List<QuestionMultiple> findByShortName(String shortName);

	Page<QuestionMultiple> findAll(Specification<QuestionMultiple> specification, Pageable pageable);
}
