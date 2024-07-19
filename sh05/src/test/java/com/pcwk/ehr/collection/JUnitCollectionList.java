package com.pcwk.ehr.collection;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
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
@ContextConfiguration(locations = {"classpath:com/pcwk/ehr/collection/collection_list_context.xml"})
public class JUnitCollectionList implements PLog {

	@Autowired
	ApplicationContext context;
	
	ListBean listBean;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.debug("===================");
		log.debug("@BeforeClass");
	}

	@Before
	public void setUp() throws Exception {
		log.debug("===================");
		log.debug("@@efore");
		log.debug("===================");
		
		listBean = (ListBean) context.getBean("listBean");
		
	}

	@After
	public void tearDown() throws Exception {
		log.debug("===================");
		log.debug("@After");
		log.debug("===================");
		
	}

	@Test
	public void test() {
		log.debug("------------------");
		log.debug("@Test");
		log.debug("------------------");
		
		List<String> addressList = listBean.getAddressList();
		
		for(String address : addressList) {
			log.debug(address);
		}
	}
	
	@Test
	public void beans() {
		log.debug("------------------");
		log.debug("@beans");
		log.debug("------------------");
		log.debug("context : " + context);
		
		assertNotNull(context);
	}

}
