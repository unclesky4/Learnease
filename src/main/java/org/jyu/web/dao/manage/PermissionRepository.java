package org.jyu.web.dao.manage;

import java.util.List;

import org.jyu.web.entity.manage.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String>{
	
	/**
	 * 针对侧边栏查询
	 * @param isCatalog
	 * @param status
	 * @return
	 */
	List<Permission> findByIsCatalogAndStatus(Boolean isCatalog, Boolean status);
}
