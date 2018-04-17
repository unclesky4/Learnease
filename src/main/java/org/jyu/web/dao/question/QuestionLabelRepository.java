package org.jyu.web.dao.question;

import java.util.List;

import org.jyu.web.entity.question.QuestionLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionLabelRepository extends JpaRepository<QuestionLabel, String> {

	List<QuestionLabel> findByName(String name);
}
