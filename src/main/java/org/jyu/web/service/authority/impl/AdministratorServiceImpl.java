package org.jyu.web.service.authority.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.jyu.web.dao.authority.AdministratorRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.entity.authority.Administrator;
import org.jyu.web.service.authority.AdministratorService;
import org.jyu.web.utils.DateUtil;
import org.jyu.web.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {
	
	@Autowired
	private AdministratorRepository administratorDao;
	
	@Transactional
	@Override
	public Result save(Administrator administrator) {
		administrator.setPwd(administrator.getPwd());
		administrator.setCreateTime(DateUtil.DateToString(DateUtil.YMD, new Date()));
		administratorDao.saveAndFlush(administrator);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(Administrator administrator, Boolean updatePassword) {
		if(updatePassword) {
			administrator.setPwd(MD5Util.MD5(administrator.getPwd()));
		}
		administratorDao.saveAndFlush(administrator);
		return new Result(true, "更新成功");
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
	public Administrator findByNameOrEmail(String name, String email) {
		return administratorDao.findByNameOrEmail(name, email);
	}

	@Override
	public Administrator findByNameAndPwd(String name, String pwd) {
		return administratorDao.findByNameAndPwd(name, pwd);
	}

	@Override
	public Administrator findByEmailAndPwd(String email, String pwd) {
		return administratorDao.findByEmailAndPwd(email, pwd);
	}

	@Override
	public Map<String, Object> finAll() {
		List<Administrator> list = administratorDao.findAll();
		Map<String, Object> map = new HashMap<>();
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}

}
