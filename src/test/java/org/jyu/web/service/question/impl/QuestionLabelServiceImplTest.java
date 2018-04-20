package org.jyu.web.service.question.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jyu.web.SpringBootStart;
import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.service.question.QuestionLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=SpringBootStart.class)
public class QuestionLabelServiceImplTest {
	
	@Autowired
	private QuestionLabelService service;

	@Test
	public void testSave() {
		QuestionLabel label = new QuestionLabel();
		label.setName("微服务架构");
		System.out.println(service.save(label).getMsg());;
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
	public void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testList() {
		fail("Not yet implemented");
	}

}
