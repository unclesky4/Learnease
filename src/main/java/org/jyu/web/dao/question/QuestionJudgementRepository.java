package org.jyu.web.dao.question;

import java.util.List;

import org.jyu.web.entity.question.QuestionJudgement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionJudgementRepository extends JpaRepository<QuestionJudgement, String> {
	
	List<QuestionJudgement> findByShortName(String shortName);
	
	List<QuestionJudgement> findAll(Specification<QuestionJudgement> specification, Sort sort);
	
	Page<QuestionJudgement> findAll(Specification<QuestionJudgement> specification, Pageable pageable);
}
