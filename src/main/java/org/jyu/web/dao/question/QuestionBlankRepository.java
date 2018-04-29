package org.jyu.web.dao.question;

import java.util.List;

import org.jyu.web.entity.question.QuestionBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionBlankRepository extends JpaRepository<QuestionBlank, String> {
	
	List<QuestionBlank> findByShortName(String shortName);
	
	List<QuestionBlank> findAll(Specification<QuestionBlank> specification, Sort sort);
	
	Page<QuestionBlank> findAll(Specification<QuestionBlank> specification, Pageable pageable);

}
