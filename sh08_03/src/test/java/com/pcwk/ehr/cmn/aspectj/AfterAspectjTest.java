package com.pcwk.ehr.cmn.aspectj;

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

@RunWith(SpringRunner.class)
@ContextConfiguration(locations= {"classpath:/com/pcwk/ehr/cmn/aspectj/before_aspectj_context.xml"})
public class AfterAspectjTest implements PLog{

	@Autowired
	ApplicationContext context;
	
	@Autowired
	Member member;
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌────────────────────────┐");
		log.debug("│@Before setUp() 		│");
		log.debug("└────────────────────────┘");
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌────────────────────────┐");
		log.debug("│@After tearDown() 		│");
		log.debug("└────────────────────────┘");
	}

	@Test
	public void afterAspectJ() {
		log.debug("┌────────────────────────┐");
		log.debug("│@Test afterAspectJ() 	│");
		log.debug("└────────────────────────┘");
		
		member.doSave();
		member.delete();
	}
	
	@Ignore
	@Test
	public void beans() {
		log.debug("┌────────────────────────┐");
		log.debug("│@Test beans() 			│");
		log.debug("└────────────────────────┘");
		
		log.debug("context : " + context);
		log.debug("member : " + member);
		assertNotNull(context);
		assertNotNull(member);
		
	}

}
