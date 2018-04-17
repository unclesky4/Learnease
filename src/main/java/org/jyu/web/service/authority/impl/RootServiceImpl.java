package org.jyu.web.service.authority.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.jyu.web.dao.authority.RootRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.entity.authority.Root;
import org.jyu.web.service.authority.RootService;
import org.jyu.web.utils.DateUtil;
import org.jyu.web.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RootServiceImpl implements RootService {
	
	@Autowired
	private RootRepository rootDao;
	
	@Transactional
	@Override
	public Result save(Root root) {
		root.setPwd(MD5Util.MD5(root.getPwd()));
		root.setCreateTime(DateUtil.DateToString(DateUtil.YMD, new Date()));
		rootDao.saveAndFlush(root);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(Root root, Boolean updatePassword) {
		if (updatePassword) {
			root.setPwd(MD5Util.MD5(root.getPwd()));
		}
		rootDao.saveAndFlush(root);
		return new Result(true, "更新成功");
	}

	@Transactional
	@Override
	public Result deleteById(String id) {
		rootDao.deleteById(id);
		return new Result(true, "删除成功");
	}

	@Transactional
	@Override
	public Result delete(List<String> ids) {
		Set<Root> set = new HashSet<>();
		for (String id : ids) {
			Root root = rootDao.getOne(id);
			if (root != null) {
				set.add(root);
			}
		}
		rootDao.deleteInBatch(set);
		return new Result(true, "删除成功");
	}

	@Override
	public Root findById(String id) {
		return rootDao.getOne(id);
	}

	@Override
	public Root findByNameOrEmail(String name, String email) {
		return rootDao.findByNameOrEmail(name, email);
	}

	@Override
	public Root findByNameAndPwd(String name, String pwd) {
		return rootDao.findByNameAndPwd(name, pwd);
	}

	@Override
	public Root findByEmailAndPwd(String email, String pwd) {
		return rootDao.findByEmailAndPwd(email, pwd);
	}
	
	@Override
	public Map<String, Object> finAll() {
		List<Root> list = rootDao.findAll();
		Map<String, Object> map = new HashMap<>();
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}

}
