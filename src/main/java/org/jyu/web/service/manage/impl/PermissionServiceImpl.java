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
import org.jyu.web.dto.manage.PermissionJson;
import org.jyu.web.dto.manage.ZtreePermission;
import org.jyu.web.entity.manage.Permission;
import org.jyu.web.service.manage.PermissionService;
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
	public List<ZtreePermission> findForZTree() {
		List<Permission> permissions = permissionDao.findAll();
		return convertData(permissions);
	}
	
	@Override
	public List<ZtreePermission> findValidPermission() {
		List<Permission> list = permissionDao.findByStatus(true);
		return convertData(list);
	}
	

	@Transactional
	@Override
	public Result save(String name, String code, Boolean isCatalog, Boolean status, String pid) {
		/*if (isCatalog == false && code == null) {
			return new Result(false, "请输入权限代码");
		}*/
		
		Permission permission = new Permission();
		permission.setName(name);
		permission.setCode(code);
		permission.setIsCatalog(isCatalog);
		permission.setStatus(status);
		if(pid != "" && pid != null) {
			permission.setPid(permissionDao.getOne(pid));
		}
		/*if (permission.getPid() != null && !permission.getPid().getIsCatalog()) {
			return new Result(false, "类型为权限时不能添加子节点");
		}*/
		
		Permission permission2 = permissionDao.saveAndFlush(permission);
		
		ZtreePermission json = new ZtreePermission();
		json.setId(permission2.getId());
		json.setName(permission2.getName());
		if (permission2.getPid() != null) {
			json.setpId(permission2.getPid().getId());
		}else {
			json.setpId("0");
		}
		json.setChecked(permission2.getStatus());
		json.setCode(permission2.getCode());
		json.setIsCatalog(permission2.getIsCatalog());
		
		return new Result(true, json);
	}

	@Transactional
	@Override
	public Result update(String id, String name, String code, Boolean isCatalog, Boolean status) {
		Permission permission = permissionDao.getOne(id);
		if(permission == null) {
			return new Result(false, "对象不存在");
		}
		if(name != null && name != "") {
			permission.setName(name);
		}
		if (isCatalog != null) {
			permission.setIsCatalog(isCatalog);
		}
		if(code != null) {
			permission.setCode(code);	
		}
		if (isCatalog == false && code == null) {
			return new Result(false, "请输入权限代码");
		}

		if(status != null) {
			permission.setStatus(status);
		}
		
		Permission permission2 = permissionDao.saveAndFlush(permission);
		
		ZtreePermission json = new ZtreePermission();
		json.setId(permission2.getId());
		json.setChecked(permission2.getStatus());
		json.setName(permission2.getName());
		if (permission2.getPid() != null) {
			json.setpId(permission2.getPid().getId());
		}else{
			json.setpId("0");
		}
		json.setCode(permission2.getCode());
		json.setIsCatalog(permission2.getIsCatalog());
		return new Result(true, json);
	}
	
	@Override
	public Result update(String id, String targetId) {
		Permission permission = permissionDao.getOne(id);
		Permission permission2 = permissionDao.getOne(targetId);
		if (permission == null) {
			return new Result(false, "对象不存在");
		}
		permission.setPid(permission2);
		permissionDao.save(permission);
		return new Result(true, "操作成功");
	}

	@Override
	public Map<String, List<PermissionJson>> findForSideBar() {
		List<Permission> permissions = permissionDao.findByIsCatalogAndStatus(true, true);
		
		Map<String, List<PermissionJson>> map = new HashMap<>();
		
		for (Permission permission : permissions) {
			if (permission.getPid() == null) {
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

	@Override
	public Result updateStatus(String upIds, String downIds) {
		String[] upId = upIds.split(",");
		String[] downId = downIds.split(",");
		
		List<Permission> list = new ArrayList<>();
		int size = upId.length;
		for (int i = 0; i < size; i++) {
			if (upId[i] == "") {
				continue;
			}
			Permission permission = permissionDao.getOne(upId[i]);
			if (permission != null) {
				permission.setStatus(true);
				list.add(permission);
			}
		}
		size = downId.length;
		for (int i = 0; i < size; i++) {
			if (downId[i] == "") {
				continue;
			}
			Permission permission = permissionDao.getOne(downId[i]);
			if (permission != null) {
				permission.setStatus(false);
				list.add(permission);
			}
		}
		permissionDao.saveAll(list);
		return new Result(true, "操作成功");
	}
	

	/**
	 * List<Permission> --> List<ZtreeJson>
	 * @param permissions
	 * @return
	 */
	List<ZtreePermission> convertData(List<Permission> permissions) {
		List<ZtreePermission> list = new ArrayList<>();
		for (Permission permission : permissions) {
			ZtreePermission json = new ZtreePermission();
			json.setId(permission.getId());
			json.setName(permission.getName());
			if (permission.getPid() != null) {
				json.setpId(permission.getPid().getId());
			}else {
				json.setpId("0");
			}
			if (permission.getStatus()) {
				json.setChecked(true);
			}
			json.setCode(permission.getCode());
			json.setIsCatalog(permission.getIsCatalog());
			list.add(json);
		}
		return list;
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
			json.setStatus(permission.getStatus());
			json.setName(permission.getName());
			if (permission.getPid() != null) {
				json.setPid(permission.getPid().getId());
			}
			list.add(json);
		}
		return list;
	}

}
