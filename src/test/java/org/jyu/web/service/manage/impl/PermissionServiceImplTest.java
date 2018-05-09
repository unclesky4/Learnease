package org.jyu.web.service.manage.impl;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jyu.web.SpringBootStart;
import org.jyu.web.dto.Result;
import org.jyu.web.service.manage.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=SpringBootStart.class)
public class PermissionServiceImplTest {

	@Autowired
	private PermissionService permissionService;
	
	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindForZTree() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		Result result = permissionService.save("sdf", "permission_html", true, true, true, null);
		System.out.println(result.getMsg());
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

}
