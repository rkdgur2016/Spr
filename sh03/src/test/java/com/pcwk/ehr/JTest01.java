package com.pcwk.ehr;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class JTest01 {

	final Logger log  = LogManager.getLogger(getClass());
	
	@Test
	public void test() {
		log.debug("====================");
		log.debug("test()");
		log.debug("====================");
	}

}
