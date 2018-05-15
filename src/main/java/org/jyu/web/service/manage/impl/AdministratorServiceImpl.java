package org.jyu.web.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jyu.web.dao.manage.AdministratorRepository;
import org.jyu.web.dao.manage.RoleRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.manage.AdministratorJson;
import org.jyu.web.entity.manage.Administrator;
import org.jyu.web.entity.manage.Role;
import org.jyu.web.service.manage.AdministratorService;
import org.jyu.web.utils.DateUtil;
import org.jyu.web.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		if (administratorDao.findByName(name) != null) {
			return new Result(false, "姓名已存在");
		}
		administrator.setName(name);
		administrator.setPwd(MD5Util.MD5(pwd));
		administrator.setSex(sex);
		administrator.setPhone(phone);
		administrator.setEmail(email);
		administrator.setCreateTime(DateUtil.DateToString(DateUtil.YMDHMS, new Date()));
		
		if (administrator.getRoles() == null) {
			administrator.setRoles(new ArrayList<>());
		}
		if (roleIds != null && roleIds != "") {
			String[] roleId = roleIds.split(",");
			for (int i = 0; i < roleId.length; i++) {
				Role role = roleDao.getOne(roleId[i]);
				if (role != null) {
					administrator.getRoles().add(role);
				}
			}
		}
		
		administratorDao.saveAndFlush(administrator);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(String id, String name, String sex, String phone, String email, String roleIds) {
		Administrator administrator = administratorDao.getOne(id);
		if (name != null && name != "" && !administrator.getName().equals(name)) {
			if (administratorDao.findByName(name) != null) {
				return new Result(false, "姓名已存在");
			}
			administrator.setName(name);
		}
		if (sex != null && sex != "" && administrator.getSex() != sex) {
			administrator.setSex(sex);
		}
		if (phone != null && phone != "") {
			administrator.setPhone(phone);
		}
		if (email != null && email != "") {
			administrator.setEmail(email);
		}
		if (roleIds != null && roleIds != "") {
			administrator.getRoles().clear();
			String[] roleId = roleIds.split(",");
			for (int j = 0; j < roleId.length; j++) {
				Role role = roleDao.getOne(roleId[j]);
				if (role != null) {
					administrator.getRoles().add(role);
				}
			}
		}
		administratorDao.saveAndFlush(administrator);
		return new Result(true, "修改成功");
	}
	
	@Transactional
	@Override
	public Result updatePwd(String id, String oldPwd, String newPwd) {
		Administrator administrator = administratorDao.getOne(id);
		if (administrator == null) {
			return new Result(false, "管理员不存在");
		}
		if (!administrator.getPwd().equals(MD5Util.MD5(oldPwd))) {
			return new Result(false, "旧密码不正确");
		}
		administrator.setPwd(MD5Util.MD5(newPwd));
		administratorDao.saveAndFlush(administrator);
		return new Result(true, "修改成功");
	}
	
	@Transactional
	@Override
	public Result updatePassword(String id, String password) {
		Administrator administrator = administratorDao.getOne(id);
		if (administrator == null) {
			return new Result(false, "管理员不存在");
		}
		administrator.setPwd(MD5Util.MD5(password));
		administratorDao.saveAndFlush(administrator);
		return new Result(true, "修改成功");
	}


	@Transactional
	@Override
	public Result deleteById(String id) {
		administratorDao.deleteById(id);
		return new Result(true, "删除成功");
	}

	@Override
	public Administrator findById(String id) {
		return administratorDao.getOne(id);
	}

	@Override
	public Map<String, Object> finAll(Integer pageNumber, Integer pageSize) {
		Sort sort = new Sort(Direction.DESC, "createTime");
		Pageable pageable = new PageRequest(pageNumber-1, pageSize, sort);
		
		Page<Administrator> page = administratorDao.findAll(pageable);
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", convertList(page.getContent()));
		return map;
	}

	@Override
	public Administrator findByName(String name) {
		return administratorDao.findByName(name);
	}
	
	List<AdministratorJson> convertList(List<Administrator> administrators) {
		List<AdministratorJson> list = new ArrayList<>();
		if (administrators == null) {
			return list;
		}
		for (Administrator administrator : administrators) {
			AdministratorJson json = new AdministratorJson();
			json.setCreateTime(administrator.getCreateTime());
			json.setEmail(administrator.getEmail());
			json.setId(administrator.getId());
			json.setName(administrator.getName());
			json.setPhone(administrator.getPhone());
			json.setSex(administrator.getSex());
			list.add(json);
		}
		return list;
	}
}
