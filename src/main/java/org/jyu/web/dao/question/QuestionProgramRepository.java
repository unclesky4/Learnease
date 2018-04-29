package org.jyu.web.dao.question;

import java.util.List;

import org.jyu.web.entity.question.QuestionProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionProgramRepository extends JpaRepository<QuestionProgram, String> {
	
	List<QuestionProgram> findByShortName(String shortName);
	
	List<QuestionProgram> findAll(Specification<QuestionProgram> specification, Sort sort);
	
	Page<QuestionProgram> findAll(Specification<QuestionProgram> specification, Pageable pageable);
}
