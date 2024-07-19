package com.pcwk.ehr.properties;

import static org.junit.Assert.assertNotNull;

import java.util.Properties;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pcwk.ehr.cmn.PLog;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:/com/pcwk/ehr/properties/collection_properties_context.xml"})
public class JUnitCollectionProperties implements PLog{

	@Autowired
	ApplicationContext context;
	
	PropertiesBean propertiesBean;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.debug("===================");
		log.debug("@BeforeClass");
	}

	@Before
	public void setUp() throws Exception {
		log.debug("===================");
		log.debug("@Before");
		log.debug("===================");

		propertiesBean = context.getBean("propertiesBean", PropertiesBean.class);
	}

	@After
	public void tearDown() throws Exception {
		log.debug("===================");
		log.debug("@After");
		log.debug("===================");
		
	}
	@Test
	public void propTest() {
		log.debug("===================");
		log.debug("@propTest()");
		log.debug("===================");
		
		Properties properties = propertiesBean.getAddressProp();
		
		Set<Object> keys = properties.keySet();
		
		for(Object key : keys) {
			log.debug(key.toString()+":" + properties.getProperty(key.toString()));
		}
		
	}
	@Test
	public void beans() {
		log.debug("─────────────────────────");
		log.debug("─────────────────────────");
		log.debug("─────────────────────────");
		System.out.println();
		log.debug("context : " + context);
		log.debug("propertiesBean : " + propertiesBean);
		assertNotNull(context);
		assertNotNull(propertiesBean);
		System.out.println();
		log.debug("─────────────────────────");
		log.debug("─────────────────────────");
		log.debug("─────────────────────────");
	}

}
