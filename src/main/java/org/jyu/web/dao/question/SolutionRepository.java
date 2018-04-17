package org.jyu.web.dao.question;

import org.jyu.web.entity.question.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, String> {

}
