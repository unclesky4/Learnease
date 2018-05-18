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
import javax.transaction.Transactional;

import org.jyu.web.dao.authority.StudentRepository;
import org.jyu.web.dao.authority.UserRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.authority.StudentJson;
import org.jyu.web.entity.authority.Student;
import org.jyu.web.entity.authority.User;
import org.jyu.web.enums.RoleEnum;
import org.jyu.web.service.authority.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository studentDao;
	
	@Autowired
	private UserRepository userDao;

	@Transactional
	@Override
	public Result save(String idCard,String stuName,String stuSex,String stuAcademy,String stuClass,
			Integer stuEntranceTime,String userId) {
		Student student = new Student();
		student.setIdCard(idCard);
		student.setStuName(stuName);
		student.setStuSex(stuSex);
		student.setStuAcademy(stuAcademy);
		student.setStuClass(stuClass);
		student.setStuEntranceTime(stuEntranceTime);
		User user = userDao.getOne(userId);
		if (user == null) {
			return new Result(false, "用户不存在");
		}
		if (user.getStudent() != null && user.getStudent().getStatus() == 1) {
			return new Result(false, "学生角色已存在");
		}
		student.setUser(user);
		student.setRole(RoleEnum.Student);
		student.setStatus(0);
		studentDao.saveAndFlush(student);
		return new Result(true, "操作成功");
	}
	
	@Transactional
	@Override
	public Result update(String stuId, String idCard,String stuName,String stuSex,String stuAcademy,String stuClass,
			Integer stuEntranceTime, Integer status) {
		Student student = studentDao.getOne(stuId);
		if (student == null) {
			return new Result(false, "对象不存在");
		}
		if (stuName != null && stuName != "") {
			student.setStuName(stuName);
		}
		if (stuSex != null && stuSex != "") {
			student.setStuSex(stuSex);
		}
		if (stuClass != null && stuClass != "") {
			student.setStuClass(stuClass);
		}
		if (stuEntranceTime != null) {
			student.setStuEntranceTime(stuEntranceTime);
		}
		if (status != null) {
			student.setStatus(status);
		}
		if (stuAcademy != null && stuAcademy != "") {
			student.setStuAcademy(stuAcademy);
		}
		studentDao.saveAndFlush(student);
		return new Result(true, "修改成功");
	}

	@Transactional
	@Override
	public Result delete(List<String> ids) {
		Set<Student> set = new HashSet<>();
		for (String id : ids) {
			Student student = studentDao.getOne(id);
			if(student != null) {
				set.add(student);
			}
		}
		studentDao.deleteInBatch(set);
		return new Result(true, "删除成功");
	}
	
	@Transactional
	@Override
	public Result delete(String id) {
		studentDao.deleteById(id);
		return new Result(true, "删除成功");
	}

	@Override
	public StudentJson findById(String id) {
		Student student = studentDao.getOne(id);
		if (student == null) {
			return null;
		}
		return convertData(student);
	}

	@Override
	public StudentJson findByIdCard(String idCard) {
		Student student = studentDao.findByIdCard(idCard);
		if (student == null) {
			return null;
		}
		return convertData(student);
	}

	@Override
	public Map<String, Object> findStudentByPage(Integer pageNumber, Integer pageSize, String sortOrder, String searchText) {
		Sort sort = new Sort(Direction.DESC, "stuEntranceTime");
		if (sortOrder.toLowerCase().equals("asc")) {
			sort = new Sort(Direction.ASC, "stuEntranceTime");
		}
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber-1, pageSize, sort);
		
		Specification<Student> specification = new Specification<Student>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
				if (searchText != "" && searchText != null) {
					Predicate p1 = cb.like(root.get("idCard"), "'%"+searchText+"%'");
					Predicate p2 = cb.like(root.get("stuName"), "'%"+searchText+"%'");
					predicate = cb.or(p1, p2);
				}
				return predicate;
			}
		};
		Page<Student> page = studentDao.findAll(specification, pageable);
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", convertList(page.getContent()));
		return map;
	}
	
	@Override
	public Map<String, Object> getNeedToVerify(Integer pageNumber, Integer pageSize) {
		
		Pageable pageable = new PageRequest(pageNumber-1, pageSize);
		
		Specification<Student> specification = new Specification<Student>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.equal(root.get("status"), 0);
				return predicate;
			}
		};
		Page<Student> page = studentDao.findAll(specification, pageable);
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", convertList(page.getContent()));
		return map;
	}
	
	
	public StudentJson convertData(Student student) {
		StudentJson json = new StudentJson();
		if (student == null) {
			return null;
		}
		json.setStuAcademy(student.getStuAcademy());
		json.setIdCard(student.getIdCard());
		json.setStatusCode(student.getStatus());
		switch (student.getStatus()) {
		case 0:
			json.setStatus("待审核");
			break;
		case 1:
			json.setStatus("审核通过");
			break;
		case 2:
			json.setStatus("审核不通过");
			break;

		default:
			break;
		}
		json.setStuClass(student.getStuClass());
		json.setStuEntranceTime(student.getStuEntranceTime());
		json.setStuId(student.getStuId());
		json.setStuName(student.getStuName());
		json.setStuSex(student.getStuSex());
		json.setUserEmail(student.getUser().getEmail());
		json.setUserId(student.getUser().getUid());
		json.setUserName(student.getUser().getName());
		return json;
	}
	
	public List<StudentJson> convertList(List<Student> students) {
		List<StudentJson> list = new ArrayList<>();
		for (Student student : students) {
			if (student == null) {
				continue;
			}
			list.add(convertData(student));
		}
		return list;
	}

}
