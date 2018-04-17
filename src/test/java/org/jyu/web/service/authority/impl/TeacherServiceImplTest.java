package org.jyu.web.service.authority.impl;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jyu.web.SpringBootStart;
import org.jyu.web.dto.Result;
import org.jyu.web.entity.authority.Teacher;
import org.jyu.web.enums.RoleEnum;
import org.jyu.web.service.authority.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=SpringBootStart.class)
public class TeacherServiceImplTest {
	
	@Autowired
	private TeacherService teacherService;

	@Test
	public void testFindByTeacherStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByIdCard() {
		System.out.println(teacherService.findByIdCard("dsgfdg").getIdCard());
	}

	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveOrUpdate() {
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindTeacherByPage() {
		fail("Not yet implemented");
	}

}
