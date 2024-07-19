/**
 * Package Name : com.pcwk.ehr.code.service <br/>
 * Class Name: CodeServiceImplTest.java <br/>
 * Description: <br/>
 * Modification imformation :
 * ------------------------------------------
 * 최초 생성일 : 2024.07.19
 *
 * ------------------------------------------
 * author : acorn
 * since  : 2020.11.23
 * version: 0.5
 * see    : <br/>
 * Copyright (C) by KandJang All right reserved.
 */
package com.pcwk.ehr.code.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.code.domain.Code;

@RunWith(SpringRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml", 
									"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //알파벳 순서로 테스트 메서드 실행
public class CodeServiceImplTest implements PLog {

	@Autowired
	ApplicationContext context;
	
	@Autowired
	CodeService codeService;
	
	Code code;
	
	List<String> codeList;
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌──────────────────────────────────────────────┐");
		log.debug("│ setUp()                                      │");
		log.debug("└──────────────────────────────────────────────┘");
		
		code = new Code();
		
		codeList = new ArrayList<String>();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		log.debug("┌──────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                   │");
		log.debug("└──────────────────────────────────────────────┘");
	}
	@Test
	public void doRetrieve() throws Exception{
		code.setMstCode("MEMBER_SEARCH");
		
		List<Code> list = this.codeService.doRetrieve(code);
		
		assertEquals(4, list.size());
	}

	@Test
	public void doRetrieveIn() throws Exception{
		codeList.add("MEMBER_SEARCH");
		codeList.add("COM_PAGE_SIZE");
		
		List<Code> list = this.codeService.doRetrieveIn(codeList);
		
		assertEquals(10, list.size());
	}
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────────┐");
		log.debug("│ beans()                                      │");
		log.debug("└──────────────────────────────────────────────┘");
		
		log.debug("context:" + context);
		log.debug("codeService:" + codeService);

		assertNotNull(context);
		assertNotNull(codeService);
	}

}
