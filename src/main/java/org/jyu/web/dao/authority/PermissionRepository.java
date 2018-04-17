package org.jyu.web.dao.authority;

import java.util.List;

import org.jyu.web.entity.authority.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String>{

	List<Permission> findByPid(Permission pid);
	
	List<Permission> findByIsRoot(Boolean param);
}
