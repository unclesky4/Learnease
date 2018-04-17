package org.jyu.web.service.authority.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jyu.web.dao.authority.TeacherRepository;
import org.jyu.web.dao.authority.UserRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.authority.TeacherJson;
import org.jyu.web.entity.authority.Teacher;
import org.jyu.web.entity.authority.User;
import org.jyu.web.enums.RoleEnum;
import org.jyu.web.service.authority.TeacherService;
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
public class TeacherServiceImpl implements TeacherService {
	
	@Autowired
	private TeacherRepository teacherDao;
	
	@Autowired
	private UserRepository userDao;

	@Override
	public TeacherJson findByIdCard(String idCard) {
		return convert(teacherDao.findByIdCard(idCard));
	}

	@Override
	public TeacherJson findById(String id) {
		return convert(teacherDao.getOne(id));
	}

	@Transactional
	@Override
	public Result delete(List<String> ids) {
		Set<Teacher> teachers = new HashSet<>();
		for (String id : ids) {
			Teacher teacher = teacherDao.getOne(id);
			if(teacher != null) {
				teachers.add(teacher);
			}
		}
		teacherDao.deleteInBatch(teachers);
		return new Result(true, "删除成功");
	}

	@Override
	public Map<String, Object> findTeacherByPage(Integer pageNumber, Integer pageSize, String sortOrder, String searchText) {
		Sort sort = new Sort(Direction.DESC, "idCard");
		if(sortOrder.toLowerCase().equals("asc")) {
			sort = new Sort(Direction.ASC);
		}
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber-1, pageSize, sort);
		
		Specification<Teacher> specification = new Specification<Teacher>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Teacher> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (searchText != "" && searchText != null) {
					Predicate p1 = cb.like(root.get("idCard"), "'%"+searchText+"%'");
					Predicate p2 = cb.like(root.get("name"), "'%"+searchText+"%'");
					predicate = cb.or(predicate, p1, p2);
				}
				return predicate;
			}
		};
		
		Page<Teacher> page = teacherDao.findAll(specification, pageable);
		List<Teacher> list = page.getContent();
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", convertData(list));
		return map;
	}

	@Transactional
	@Override
	public Result save(String name, String sex, String idCard, String subject, String phone, String userId) {
		Teacher teacher = new Teacher();
		teacher.setName(name);
		teacher.setSex(sex);
		teacher.setIdCard(idCard);
		teacher.setSubject(subject);
		teacher.setPhone(phone);
		teacher.setStatus(0);
		teacher.setRole(RoleEnum.Teacher);
		User user = userDao.getOne(userId);
		if (user == null) {
			return new Result(false, "用户对象不存在");
		}
		teacher.setUser(user);
		teacherDao.saveAndFlush(teacher);
		return new Result(true, "操作成功");
	}

	@Transactional
	@Override
	public Result update(String tid, String name, String sex, String idCard, String subject, String phone,
			Integer status) {
		Teacher teacher = teacherDao.getOne(tid);
		if (teacher == null) {
			return new Result(false, "对象不存在");
		}
		if (name != null && name != "") {
			teacher.setName(name);
		}
		if (idCard != null && idCard != "") {
			teacher.setIdCard(idCard);
		}
		if (subject != null && subject != "") {
			teacher.setSubject(subject);
		}
		if (phone != null && phone != "") {
			teacher.setPhone(phone);
		}
		if (status != null) {
			teacher.setStatus(status);
		}
		teacherDao.saveAndFlush(teacher);
		return new Result(true, "修改成功");
	}
	
	TeacherJson convert(Teacher teacher) {
		if (teacher == null) {
			return null;
		}
		TeacherJson json = new TeacherJson();
		json.setIdCard(teacher.getIdCard());
		json.setName(teacher.getName());
		json.setPhone(teacher.getPhone());
		json.setSex(teacher.getSex());
		json.setStatus_code(teacher.getStatus());
		json.setStatus(teacher.getStatus() == 0 ? "未审核" : "已审核");
		json.setSubject(teacher.getSubject());
		json.setTid(teacher.getTid());
		json.setUserEmail(teacher.getUser().getEmail());
		json.setUserName(teacher.getUser().getName());
		json.setUserId(teacher.getUser().getUid());
		return json;
	}
	
	
	List<TeacherJson> convertData(List<Teacher> teachers) {
		List<TeacherJson> list = new ArrayList<>();
		for (Teacher teacher : teachers) {
			TeacherJson json = new TeacherJson();
			json.setIdCard(teacher.getIdCard());
			json.setName(teacher.getName());
			json.setPhone(teacher.getPhone());
			json.setSex(teacher.getSex());
			json.setStatus_code(teacher.getStatus());
			json.setStatus(teacher.getStatus() == 0 ? "未审核" : "已审核");
			json.setSubject(teacher.getSubject());
			json.setTid(teacher.getTid());
			json.setUserEmail(teacher.getUser().getEmail());
			json.setUserName(teacher.getUser().getName());
			json.setUserId(teacher.getUser().getUid());
			list.add(json);
		}
		return list;
	}

}
