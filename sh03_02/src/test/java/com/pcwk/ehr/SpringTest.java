package com.pcwk.ehr;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class SpringTest {
	
	final static Logger log  = LogManager.getLogger(SpringTest.class);
	
	@Autowired //텍스트 오브젝트가 만들어지고 나면 스프링 테스트 컨텍스트에 의해 자동으로 주입된다.
	ApplicationContext context;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.debug("────────────────────────────");
		log.debug("@BeforeClass");
		log.debug("────────────────────────────");
	}

	@Before //어노텐션과 메서드 이름이 다를 수 있다. 어노텐션이 중요하다.
	public void setUp() throws Exception {
		log.debug("────────────────────────────");
		log.debug("@Before");
		log.debug("────────────────────────────");
	}

	@Test
	public void contextTest() {
		log.debug("────────────────────────────");
		log.debug("contextTest()");
		log.debug("context : " + context);
		assertNotNull("ApplicationContext not null", context);
		log.debug("────────────────────────────");

		
	}
	
	@Test
	public void beans() {
		log.debug("────────────────────────────");
		log.debug("beans()");
		log.debug("context : " + context);
		log.debug("────────────────────────────");
		
		assertNotNull("ApplicationContext not null", context);
	}

}
