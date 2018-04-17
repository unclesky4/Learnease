package org.jyu.web.service.authority.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.jyu.web.dao.authority.PermissionRepository;
import org.jyu.web.dao.authority.RoleRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.entity.authority.Permission;
import org.jyu.web.entity.authority.Role;
import org.jyu.web.service.authority.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleDao;
	
	@Autowired
	private PermissionRepository permissionDao;

	@Override
	public Role findById(String id) {
		return roleDao.getOne(id);
	}

	@Override
	public Map<String, Object> findAll() {
		List<Role> list = roleDao.findAll();
		Map<String, Object> map = new HashMap<>();
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	
	@Override
	public List<Role> list() {
		return roleDao.findAll();
	}

	@Transactional
	@Override
	public Result save(String name, String code) {
		if(name == "" || name == null || code == "" || code == null) {
			return new Result(false, "参数值错误");
		}
		Role role = new Role();
		role.setCode(code);
		role.setName(name);
		roleDao.saveAndFlush(role);
		return new Result(true, "保存成功");
	}
	
	@Transactional
	@Override
	public Result update(String id, String name, String code, List<String> permissionIds) {
		Role role = roleDao.getOne(id);
		if(role == null) {
			return new Result(false, "对象不存在");
		}
		if(name != null && name != "") {
			role.setName(name);
		}
		if (code != null && code != "") {
			role.setCode(code);
		}
		if(permissionIds.size() > 0) {
			if(role.getPermissions() != null) {
				role.getPermissions().clear();
			}else{
				role.setPermissions(new ArrayList<Permission>());
			}
			
			for (String pid : permissionIds) {
				Permission permission = permissionDao.getOne(pid);
				if(permission != null) {
					role.getPermissions().add(permission);
				}
			}
		}
		roleDao.saveAndFlush(role);
		return new Result(true, "更新成功");
	}

	@Transactional
	@Override
	public Result deleteById(String id) {
		roleDao.deleteById(id);
		return new Result(true, "删除成功");
	}

	@Override
	public Result delete(List<String> ids) {
		Set<Role> set = new HashSet<>();
		for (String id : ids) {
			Role role = roleDao.getOne(id);
			if(role != null) {
				set.add(role);
			}
		}
		roleDao.deleteInBatch(set);
		return new Result(true, "删除成功");
	}

}
