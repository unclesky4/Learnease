package org.jyu.web.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.jyu.web.dao.manage.AdministratorRepository;
import org.jyu.web.dao.manage.RoleRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.entity.manage.Administrator;
import org.jyu.web.entity.manage.Role;
import org.jyu.web.service.manage.AdministratorService;
import org.jyu.web.utils.DateUtil;
import org.jyu.web.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {
	
	@Autowired
	private AdministratorRepository administratorDao;
	
	@Autowired
	private RoleRepository roleDao;
	
	@Transactional
	@Override
	public Result save(String name, String pwd, String sex, String phone, String email, String roleIds) {
		Administrator administrator = new Administrator();
		administrator.setName(name);
		administrator.setPwd(MD5Util.MD5(pwd));
		administrator.setSex(sex);
		administrator.setPhone(phone);
		administrator.setEmail(email);
		administrator.setCreateTime(DateUtil.DateToString(DateUtil.YMDHMS, new Date()));
		
		if (administrator.getRoles() == null) {
			administrator.setRoles(new ArrayList<>());
		}
		String[] roleId = roleIds.split(",");
		for (int i = 0; i < roleId.length; i++) {
			Role role = roleDao.getOne(roleId[i]);
			if (role != null) {
				administrator.getRoles().add(role);
			}
		}
		
		administratorDao.saveAndFlush(administrator);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(String id, String name, String pwd, String sex, String phone, String email, 
			String roleIds, Boolean updatePassword) {
		Administrator administrator = administratorDao.getOne(id);
		if (name != null && name != "") {
			administrator.setName(name);
		}
		if (sex != null && sex != "") {
			administrator.setSex(sex);
		}
		if (phone != null && phone != "") {
			administrator.setPhone(phone);
		}
		if (email != null && email != "") {
			administrator.setEmail(email);
		}
		if(updatePassword && pwd != null && pwd != "") {
			administrator.setPwd(MD5Util.MD5(administrator.getPwd()));
		}
		if (roleIds != null && roleIds != "") {
			administrator.getRoles().clear();
			String[] roleId = roleIds.split(",");
			
		}
		administratorDao.saveAndFlush(administrator);
		return new Result(true, "修改成功");
	}

	@Transactional
	@Override
	public Result deleteById(String id) {
		administratorDao.deleteById(id);
		return new Result(true, "删除成功");
	}

	@Transactional
	@Override
	public Result delete(List<String> ids) {
		Set<Administrator> administrators = new HashSet<>();
		for (String id : ids) {
			Administrator administrator = administratorDao.getOne(id);
			if(administrator != null) {
				administrators.add(administrator);
			}
		}
		administratorDao.deleteInBatch(administrators);
		return new Result(true, "删除成功");
		
	}

	@Override
	public Administrator findById(String id) {
		return administratorDao.getOne(id);
	}

	@Override
	public Map<String, Object> finAll() {
		List<Administrator> list = administratorDao.findAll();
		Map<String, Object> map = new HashMap<>();
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}

	@Override
	public Administrator findByName(String name) {
		return administratorDao.findByName(name);
	}

}
