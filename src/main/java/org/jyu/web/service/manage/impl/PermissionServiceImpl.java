package org.jyu.web.service.manage.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.jyu.web.dao.manage.PermissionRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.ZtreeJson;
import org.jyu.web.dto.manage.PermissionJson;
import org.jyu.web.entity.manage.Permission;
import org.jyu.web.service.manage.PermissionService;
import org.jyu.web.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionRepository permissionDao;

	@Override
	public Permission findById(String id) {
		return permissionDao.getOne(id);
	}

	@Override
	public Map<String, Object> findAll() {
		List<Permission> list = permissionDao.findAll();
		Map<String, Object> map = new HashMap<>();
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}

	@Transactional
	@Override
	public Result delete(List<String> ids) {
		Set<Permission> set = new HashSet<>();
		for (String id : ids) {
			Permission permission = permissionDao.getOne(id);
			if(permission != null) {
				set.add(permission);
			}
		}
		permissionDao.deleteInBatch(set);
		return new Result(true, "删除成功");
	}

	@Override
	public List<ZtreeJson> findForZTree(String id) {
		List<ZtreeJson> list = new ArrayList<>();
		
		List<Permission> permissions = new ArrayList<>();
		//zTree初始化时不传id,name,level -- 获取权限根结点
		if(id == null) {
			permissions = permissionDao.findByIsRootAndIsMenuShow(true, true);
			for (Permission permission : permissions) {
				ZtreeJson tmp = new ZtreeJson();
				tmp.setId(permission.getId());
				tmp.setName(permission.getName());
				if(permission.getIsCatalog()) {
					tmp.setIsParent(true);
				}else{
					tmp.setIsParent(false);
				}
				list.add(tmp);
			}
			return list;
		}
		
		//获取权限节点的子节点权限
		Permission pid = permissionDao.getOne(id);
		permissions = permissionDao.findByPid(pid);
		for (Permission permission : permissions) {
			if(permission.getPid().getId().equals(id)) {
				ZtreeJson tmp = new ZtreeJson();
				tmp.setId(permission.getId());
				tmp.setName(permission.getName());
				if(permission.getIsCatalog()) {
					tmp.setIsParent(true);
				}else{
					tmp.setIsParent(false);
				}
				list.add(tmp);
			}
		}
		
		return list;
	}

	@Transactional
	@Override
	public Result save(String name, String code, Boolean isRoot, Boolean isCatalog, Boolean isMenuShow, String pid) {
		Permission permission = new Permission();
		permission.setName(name);
		permission.setCode(code);
		permission.setIsRoot(isRoot);
		permission.setIsCatalog(isCatalog);
		if (isRoot) {
			permission.setIsCatalog(true);
		}
		permission.setIsMenuShow(isMenuShow);
		if(pid != "" && pid != null) {
			permission.setPid(permissionDao.getOne(pid));
		}
		
		Permission permission2 = permissionDao.saveAndFlush(permission);
		return new Result(true, permission2.getId());
	}

	@Transactional
	@Override
	public Result update(String id, String name, String code, Boolean isRoot, Boolean isCatalog, Boolean isMenuShow) {
		Permission permission = permissionDao.getOne(id);
		if(permission == null) {
			return new Result(false, "对象不存在");
		}
		if(name != null && name != "") {
			permission.setName(name);
		}
		
		if(code != null) {
			permission.setCode(code);	
		}
		
		if(isRoot != null) {
			permission.setIsRoot(isRoot);
		}
		
		if(isCatalog != null) {
			permission.setIsCatalog(isCatalog);
		}
		if (isRoot) {
			permission.setIsCatalog(true);
		}
		
		if(isMenuShow != null) {
			permission.setIsMenuShow(isMenuShow);
		}
		
		Permission permission2 = permissionDao.saveAndFlush(permission);
		return new Result(true, JsonUtil.toJson(permission2));
	}

	@Override
	public Map<String, List<PermissionJson>> findForSideBar() {
		List<Permission> permissions = permissionDao.findByIsMenuShowAndIsCatalog(true, true);
		
		Map<String, List<PermissionJson>> map = new HashMap<>();
		
		for (Permission permission : permissions) {
			if (permission.getIsRoot()) {
				List<PermissionJson> list = new ArrayList<>();
				for (Permission permission1 : permissions) {
					if (permission1.getPid() != null && permission.getId().equals(permission1.getPid().getId())) {
						list.add(convert(permission1));
					}
				}
				if (list.size()>0) {
					map.put(permission.getName(), list);
				}else{
					map.put(permission.getName(), null);
				}
			}
		}
		
		return map;
	}
	
	/**
	 * Permission --> PermissionJson
	 * @param permission
	 * @return
	 */
	PermissionJson convert(Permission permission) {
		PermissionJson json = new PermissionJson();
		json.setCode(permission.getCode());
		json.setId(permission.getId());
		json.setIsCatalog(permission.getIsCatalog());
		json.setIsMenuShow(permission.getIsMenuShow());
		json.setIsRoot(permission.getIsRoot());
		json.setName(permission.getName());
		if (permission.getPid() != null) {
			json.setPid(permission.getPid().getId());
		}
		return json;
	}
	
	/**
	 * List<Permission> --> List<PermissionJson>
	 * @param permissions
	 * @return
	 */
	List<PermissionJson> convertList(List<Permission> permissions) {
		List<PermissionJson> list = new ArrayList<>();
		for (Permission permission : permissions) {
			PermissionJson json = new PermissionJson();
			json.setCode(permission.getCode());
			json.setId(permission.getId());
			json.setIsCatalog(permission.getIsCatalog());
			json.setIsMenuShow(permission.getIsMenuShow());
			json.setIsRoot(permission.getIsRoot());
			json.setName(permission.getName());
			if (permission.getPid() != null) {
				json.setPid(permission.getPid().getId());
			}
			list.add(json);
		}
		return list;
	}

}
