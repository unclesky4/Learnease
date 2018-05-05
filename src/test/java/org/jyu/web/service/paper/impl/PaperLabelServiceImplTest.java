package org.jyu.web.service.paper.impl;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jyu.web.SpringBootStart;
import org.jyu.web.service.paper.PaperLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=SpringBootStart.class)
public class PaperLabelServiceImplTest {
	
	@Autowired
	private PaperLabelService paperLabelService;

	@Test
	public void testSave() {
		System.out.println(paperLabelService.save("dgdsjfg").getMsg());
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteByid() {
		fail("Not yet implemented");
	}

	@Test
	public void testPageJson() {
		fail("Not yet implemented");
	}

	@Test
	public void testList() {
		fail("Not yet implemented");
	}

}
