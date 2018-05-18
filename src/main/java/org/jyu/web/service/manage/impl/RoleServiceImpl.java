package org.jyu.web.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.jyu.web.dao.manage.PermissionRepository;
import org.jyu.web.dao.manage.RoleRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.manage.RoleJson;
import org.jyu.web.dto.manage.ZtreePermission;
import org.jyu.web.entity.manage.Permission;
import org.jyu.web.entity.manage.Role;
import org.jyu.web.service.manage.RoleService;
import org.jyu.web.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
	public Map<String, Object> findAll(Integer pageNumber, Integer pageSize) {
		Sort sort = new Sort(Direction.DESC, "createTime");
		@SuppressWarnings("deprecation")
		Pageable Pageable = new PageRequest(pageNumber-1, pageSize, sort);
		Page<Role> page = roleDao.findAll(Pageable);
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", convertList(page.getContent()));
		return map;
	}
	
	@Override
	public List<RoleJson> list() {
		return convertList(roleDao.findAll());
	}

	@Transactional
	@Override
	public Result save(String name, String code, String description) {
		Role tmp = roleDao.findByCode(code);
		if (tmp != null) {
			return new Result(false, "角色代码已存在");
		}
		Role role = new Role();
		role.setCode(code);
		role.setName(name);
		role.setDescription(description);
		role.setCreateTime(DateUtil.DateToString(DateUtil.YMDHMS, new Date()));
		roleDao.saveAndFlush(role);
		return new Result(true, "保存成功");
	}
	
	@Transactional
	@Override
	public Result update(String id, String name, String code, String description, List<String> permissionIds) {
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
		if (description != null) {
			role.setDescription(description);
		}
		if(permissionIds != null && permissionIds.size() > 0) {
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
	
	/**
	 * List<Role> --> List<RoleJson>
	 * @param list  Role集合
	 * @return
	 */
	List<RoleJson> convertList(List<Role> list) {
		List<RoleJson> roleJsons = new ArrayList<>();
		for (Role role : list) {
			RoleJson json = new RoleJson();
			json.setCode(role.getCode());
			json.setDescription(role.getDescription());
			json.setId(role.getId());
			json.setName(role.getName());
			roleJsons.add(json);
		}
		return roleJsons;
	}

	@Override
	public List<ZtreePermission> findRolePermissions(String id) {
		List<ZtreePermission> list = new ArrayList<>();
		
		Role role = roleDao.getOne(id);
		if (role == null || role.getPermissions() == null) {
			return list;
		}
		List<Permission> permissions = role.getPermissions();
		//System.out.println(permissions.size());
		for (Permission permission : permissions) {
			if (!permission.getStatus()) {
				continue;
			}
			ZtreePermission json = new ZtreePermission();
			json.setChecked(permission.getStatus());
			json.setCode(permission.getCode());
			json.setId(permission.getId());
			json.setIsCatalog(permission.getIsCatalog());
			json.setName(permission.getName());
			if (permission.getPid() != null) {
				json.setpId(permission.getPid().getId());
			}
			list.add(json);
		}
		return list;
	}

	@Override
	public Role findByCode(String code) {
		return roleDao.findByCode(code);
	}

}
