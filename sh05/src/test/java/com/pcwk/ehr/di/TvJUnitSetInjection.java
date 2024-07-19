package com.pcwk.ehr.di;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pcwk.ehr.cmn.PLog;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:/com/pcwk/ehr/di/set_applicationContext.xml"})
public class TvJUnitSetInjection implements PLog{

	@Autowired
	ApplicationContext context;
	
	Tv tv01;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.debug("^^^setUpBeforeClass: ");
	}

	@Before
	public void setUp() throws Exception {
		tv01 = (LGTv)context.getBean("lgTv");
	}

	@Test
	public void beans() {
		log.debug("context : " + context);
		log.debug("tv01 : " + tv01);
		assertNotNull(context);
		assertNotNull(tv01);
	}
	
	@

}
