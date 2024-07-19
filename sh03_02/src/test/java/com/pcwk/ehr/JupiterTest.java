package com.pcwk.ehr;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) //인스턴스를 한개씩만 생성해라
class JupiterTest {

	final static Logger log  = LogManager.getLogger(JupiterTest.class);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		log.debug("────────────────────────────");
		log.debug("@@BeforeAll");
		log.debug("────────────────────────────");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		log.debug("────────────────────────────");
		log.debug("@@AfterAll");
		log.debug("────────────────────────────");
	}

	@BeforeEach
	void setUp() throws Exception {
		log.debug("────────────────────────────");
		log.debug("@@BeforeEach");
		log.debug("────────────────────────────");
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("────────────────────────────");
		log.debug("@@AfterEach");
		log.debug("────────────────────────────");
	}

	@Test
	void test() {
		log.debug("────────────────────────────");
		log.debug("@@Test");
		log.debug("────────────────────────────");
	}
	
	@Test
	void test2() {
		log.debug("────────────────────────────");
		log.debug("@@Test2");
		log.debug("────────────────────────────");
	}

}
