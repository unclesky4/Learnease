package org.jyu.web.dao.manage;

import java.util.List;

import org.jyu.web.entity.manage.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String>{

	List<Permission> findByPid(Permission pid);
	
	/**
	 * 查询有效的根结点权限
	 * @param param
	 * @param isMenuShow
	 * @return
	 */
	List<Permission> findByIsRootAndIsMenuShow(Boolean param, Boolean isMenuShow);
	
	/**
	 * 针对侧边栏查询
	 * @param status
	 * @param isCatalog
	 * @return
	 */
	List<Permission> findByIsMenuShowAndIsCatalog(Boolean isMenuShow, Boolean isCatalog);
}
