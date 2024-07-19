package com.pcwk.ehr.annotation;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.di.LGTv;
import com.pcwk.ehr.di.Tv;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:/com/pcwk/ehr/di/annotation_context.xml"})
public class JUnitAnnotation implements PLog{
	
	@Autowired
	ApplicationContext context;

	Tv tv01;
	
	@Before
	public void setUp() throws Exception {
		log.debug("================================");
		log.debug("@Before");
		log.debug("================================");
		
		tv01 = context.getBean("lgTv", LGTv.class);
	}

	@After
	public void tearDown() throws Exception {
		log.debug("================================");
		log.debug("@After");
		log.debug("================================");
	}

	@Test
	public void tvTest() {
		log.debug("================================");
		log.debug("@tvTest");
		log.debug("================================");
		tv01.turnOn();
		tv01.playSound();
		tv01.turnOff();
		
	}
	@Ignore
	@Test
	public void beans() {
		log.debug("================================");
		log.debug("context : " + context);
		log.debug("tv01 : " + tv01);
		assertNotNull(context);
		assertNotNull(tv01);
		log.debug("================================");
	}

}
