package org.jyu.web.dao.question;

import org.jyu.web.entity.question.QuestionBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionBlankRepository extends JpaRepository<QuestionBlank, String> {
	
	Page<QuestionBlank> findAll(Specification<QuestionBlank> specification);

}
