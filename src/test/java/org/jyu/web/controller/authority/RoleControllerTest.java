package org.jyu.web.controller.authority;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jyu.web.SpringBootStart;
import org.jyu.web.controller.authority.RoleController;
import org.jyu.web.dto.Result;
import org.jyu.web.entity.authority.Role;
import org.jyu.web.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=SpringBootStart.class)
public class RoleControllerTest {
	
	@Autowired
	private RoleController roleController;

	@Test
	public void testSave() {
		Result result = roleController.save("管理员", "admin");
		System.out.println(JsonUtil.toJson(result));
	}

	@Test
	public void testList() {
		List<Role> list = roleController.list();
		System.out.println(JsonUtil.toJson(list));
	}

	@Test
	public void testGetById() {
		Role role = roleController.getById("ff8080816267cced016267cd07c70000");
		System.out.println(role.getName());
		
	}

	@Test
	public void testDeleteById() {
		fail("Not yet implemented");
	}

}
