package org.jyu.web.dao.question;

import org.jyu.web.entity.question.QuestionProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionProgramRepository extends JpaRepository<QuestionProgram, String> {

	Page<QuestionProgram> findAll(Specification<QuestionProgram> specification);
}
