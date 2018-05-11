package org.jyu.web.service.authority.impl;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jyu.web.SpringBootStart;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.ZtreeJson;
import org.jyu.web.service.manage.PermissionService;
import org.jyu.web.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=SpringBootStart.class)
public class PermissionServiceImplTest {
	
	@Autowired
	private PermissionService service;

	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAll() {
		Map<String, Object> map = service.findAll();
		System.out.println(map == null);
		System.out.println(map);
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindForZTree() {
		List<ZtreeJson> list = service.findForZTree();
		System.out.println(list.size());
		System.out.println(JsonUtil.toJson(list));
	}

	@Test
	public void testSave() {
//		Result result = service.save("用户查询", "user_html", false, true, true, "ff8080816279a7ad016279a7c8d80000");
//		System.out.println(result.getSuccess());
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

}
