package org.jyu.web.dao.manage;

import org.jyu.web.entity.question.QuestionLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionLabelRepository extends JpaRepository<QuestionLabel, String> {

	QuestionLabel findByName(String name);
}
