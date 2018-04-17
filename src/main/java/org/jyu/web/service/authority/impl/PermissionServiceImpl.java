package org.jyu.web.service.authority.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.jyu.web.dao.authority.PermissionRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.ZtreeJson;
import org.jyu.web.entity.authority.Permission;
import org.jyu.web.service.authority.PermissionService;
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
			permissions = permissionDao.findByIsRoot(true);
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
		
		if(isMenuShow != null) {
			permission.setIsMenuShow(isMenuShow);
		}
		
		Permission permission2 = permissionDao.saveAndFlush(permission);
		return new Result(true, JsonUtil.toJson(permission2));
	}

}
