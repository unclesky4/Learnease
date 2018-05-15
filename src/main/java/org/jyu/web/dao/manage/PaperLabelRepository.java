package org.jyu.web.dao.manage;

import org.jyu.web.entity.paper.PaperLabel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperLabelRepository extends JpaRepository<PaperLabel, String> {
	
	PaperLabel findByName(String name);
	
	Page<PaperLabel> findAll(Pageable pageable);

}
