package org.jyu.web.service.manage.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jyu.web.SpringBootStart;
import org.jyu.web.dto.Result;
import org.jyu.web.service.manage.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=SpringBootStart.class)
public class AdministratorServiceImplTest {
	
	@Autowired
	AdministratorService adminService;

	@Test
	public void testSave() {
		Result result = adminService.save("uncle", "123456", "男", "18814383363", "asdb@qq.com", "ff8080816335be88016335beb37e0000");
		System.out.println(result.getMsg());
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteById() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByNameOrEmail() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByNameAndPwd() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByEmailAndPwd() {
		fail("Not yet implemented");
	}

	@Test
	public void testFinAll() {
		fail("Not yet implemented");
	}

}
