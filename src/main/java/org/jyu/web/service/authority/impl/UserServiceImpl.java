package org.jyu.web.service.authority.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jyu.web.dao.authority.UserRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.authority.UserJson;
import org.jyu.web.entity.authority.User;
import org.jyu.web.service.authority.UserService;
import org.jyu.web.utils.DateUtil;
import org.jyu.web.utils.MD5Util;
import org.jyu.web.utils.MailUtil;
import org.jyu.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userDao;
	
	@Transactional
	@Override
	public Result save(String name,String email,String pwd) {
		if (userDao.findByEmail(email) != null) {
			return new Result(false, "邮箱已存在");
		}
		User user = new User();
		String code = UUIDUtil.getUUID();
		user.setCode(code);
		user.setRegisterTime(DateUtil.DateToString(DateUtil.YMDHMS, new Date()));
		user.setDeadline(DateUtil.deadLine(user.getRegisterTime(), 24 * 60));
		user.setEmail(email);
		user.setName(name);
		user.setPwd(MD5Util.MD5(pwd));
		user.setValidation(0);
		userDao.saveAndFlush(user);
		
		return new Result(true, code);
	}

	@Transactional
	@Override
	public Result update(String id, String name, Integer validation) {
		User user = userDao.getOne(id);
		if (user == null) {
			return new Result(false, "对象不存在");
		}
		if (name != null && name != "") {
			user.setName(name);
		}
		if (validation != null) {
			user.setValidation(validation);
		}
		userDao.saveAndFlush(user);
		return new Result(true, "修改成功");
	}
	
	@Transactional
	@Override
	public Result updatePwd(String id, String oldPwd, String newPwd) {
		User user = userDao.getOne(id);
		if (user == null) {
			return new Result(false, "用户不存在,请登陆");
		}
		if (!user.getPwd().equals(MD5Util.MD5(oldPwd))) {
			return new Result(false, "旧密码不正确");
		}
		user.setPwd(MD5Util.MD5(newPwd));
		return new Result(true, "修改成功");
	}

	@Transactional
	@Override
	public Result delete(List<String> uids) {
		Set<User> users = new HashSet<>();
		for (String id : uids) {
			User user = userDao.getOne(id);
			if(user != null) {
				users.add(user);
			}
		}
		userDao.deleteInBatch(users);
		return new Result(false, "删除成功");
	}

	@Override
	public User findById(String uid) {
		return userDao.getOne(uid);
	}

	@Override
	public List<UserJson> findByEmailLike(String email) {
		List<User> list = userDao.findByEmailLike(email);
		return convertData(list);
	}

	@Override
	public User findByEmail(String email) {
		User user = userDao.findByEmail(email);
		return user;
	}

	@Override
	public User findByEmailAndPwd(String email, String pwd) {
		pwd = MD5Util.MD5(pwd);
		return userDao.findByEmailAndPwd(email, pwd);
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> findUserByPage(Integer pageNumber, Integer pageSize, String sortOrder, String searchText) {
		sortOrder = sortOrder.toLowerCase();
		Sort sort = new Sort(Direction.DESC, "registerTime");
		if(sortOrder.equals("asc")) {
			sort = new Sort(Direction.ASC, "registerTime");
		}
		Pageable pageable = new PageRequest(pageNumber-1, pageSize,sort);
		Specification<User> specification = new Specification<User>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (searchText != "" && searchText != null) {
					Predicate p1 = cb.like(root.get("email"), "'%"+searchText+"%'");
					Predicate p2 = cb.like(root.get("name"), "'%"+searchText+"%'");
					predicate = cb.or(predicate, p1, p2);
				}
				return predicate;
			}
		};
		
		Page<User> page = userDao.findAll(specification, pageable);
		
		List<User> users = page.getContent();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", convertData(users));
		return map;
	}

	@Transactional
	@Override
	public Result deleteById(String uid) {
		userDao.deleteById(uid);
		return new Result(false, "删除成功");
	}

	@Transactional
	@Override
	public Result validate(String code) {
		User user = userDao.findByCode(code);
		if (user == null) {
			return new Result(false, "邮箱不存在");
		}
		if (user.getValidation() == 1) {
			return new Result(false, "邮箱已验证，请勿重复验证");
		}
		if (user.getDeadline().compareTo(DateUtil.DateToString(DateUtil.YMDHMS, new Date())) < 0) {
			userDao.delete(user);
			return new Result(false, "链接有效期已过，须重新注册");
		}
		user.setValidation(1);
		userDao.saveAndFlush(user);
		return new Result(true, "邮箱已验证成功");
	}

	@Transactional
	@Override
	public Result resetPassword(String code, String pwd) {
		User user = userDao.findByCode(code);
		if (user == null) {
			return new Result(false, "该邮件已失效！");
		}
		user.setPwd(MD5Util.MD5(pwd));
		user.setCode(UUIDUtil.getUUID());
		userDao.saveAndFlush(user);
		return new Result(true, "您已成功重置密码！");
	}
	
	UserJson convert(User user) {
		UserJson json = new UserJson();
		json.setEmail(user.getEmail());
		json.setName(user.getName());
		json.setRegisterTime(user.getRegisterTime());
		json.setUid(user.getUid());
		json.setValidation(user.getValidation() == 0 ?"未验证":"已验证");
		return json;
	}

	List<UserJson> convertData(List<User> users) {
		List<UserJson> list = new ArrayList<>();
 		for (User user : users) {
 			UserJson json = new UserJson();
 			json.setEmail(user.getEmail());
 			json.setName(user.getName());
 			json.setRegisterTime(user.getRegisterTime());
 			json.setUid(user.getUid());
 			json.setValidation(user.getValidation() == 0 ?"未验证":"已验证");
 			list.add(json);
		}
 		return list;
	}
}
