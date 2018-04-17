package org.jyu.web.dao.question;

import org.jyu.web.entity.question.QuestionJudgement;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionJudgementRepository extends JpaRepository<QuestionJudgement, String> {

	Page<QuestionJudgement> findAll(Specification<QuestionJudgement> specification);
}
