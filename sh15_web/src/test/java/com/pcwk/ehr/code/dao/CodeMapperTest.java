package com.pcwk.ehr.code.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
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
import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.code.domain.Code;
import com.pcwk.ehr.mapper.CodeMapper;

@RunWith(SpringRunner.class) //스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
								   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //알파벳 순서로 테스트 메서드 실행
public class CodeMapperTest implements PLog{
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	CodeMapper codeMapper;
	
	Code code;
	ArrayList<String> list;
	Search  search;
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		code = new Code();
		list = new ArrayList<String>();
		list.add("MEMBER_SEARCH");
		list.add("COM_PAGE_SIZE");
		
		search = new Search();
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");	
	}

	@Test
	public void doRetrieveIn() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ doRetrieveIn()                                          │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		List<Code> codeList = codeMapper.doRetrieveIn(list);
		log.debug("┌"); 
		for(Code vo : codeList) {
			log.debug("│vo() :"+vo); 
		}
		log.debug("└");
		assertEquals(8, codeList.size());
	}
	
	@Ignore
	@Test
	public void doRetrieve() throws Exception	{
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ doRetrieve()                                          	 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	
		code.setMstCode("MEMBER_SEARCH");
		List<Code> codeList = codeMapper.doRetrieve(code);
		
		log.debug("┌"); 
		for(Code vo : codeList) {
			log.debug("│vo() :"+vo); 
		}
		log.debug("└");
		
		assertEquals(3, codeList.size());
	}
	
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                            	  │");
		log.debug("└──────────────────────────────────────────┘");	
		
		log.debug("context : " + context);
		log.debug("codeMapper : " + codeMapper);
		
		assertNotNull(context);
		assertNotNull(codeMapper);
	}

}
