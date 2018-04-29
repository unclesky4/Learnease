package org.jyu.web.dao.question;

import org.jyu.web.entity.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, String> {

}
