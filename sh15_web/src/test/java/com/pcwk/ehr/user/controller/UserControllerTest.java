package com.pcwk.ehr.user.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.Message;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

@WebAppConfiguration //web테스트 어노테이션
@RunWith(SpringJUnit4ClassRunner.class) //스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
								   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //알파벳 순서로 테스트 메서드 실행
public class UserControllerTest implements PLog{

	@Autowired
	WebApplicationContext webApplicationContext; 
	
	//브라우저 대신 Mock
	MockMvc mockMvc;
	
	List<User> users;
	
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ UserControllerTest : setUp()             │");
		log.debug("└──────────────────────────────────────────┘");
		
		//브라우저 없이 컨트롤러 호출 가능
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		users = Arrays.asList(
				 new User("james01","이상무01","4321","2002/12/31",Level.BASIC,1,0,"rkdgur2016@naver.com","sysdate사용"),
				 new User("james02","이상무02","4321","2002/12/30",Level.SILVER,50,2,"rkdgur2017@naver.com","sysdate사용"),
				 new User("james03","이상무03","4321","2002/12/29",Level.GOLD,100,31,"rkdgur2018@naver.com","sysdate사용"),
				 new User("james04","이상무04","4321","2002/12/31",Level.BASIC,1,0,"rkdgur2019@aver.com","sysdate사용"),
				 new User("james05","이상무05","4321","2002/12/30",Level.SILVER,50,2,"rkdgur2020@naver.com","sysdate사용"));
		
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ tearDown()                       		  │");
		log.debug("└──────────────────────────────────────────┘");
	}
	
	@Test
	public void doDelete() throws Exception	{
		
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ UserControllerTest : doDelete()          │");
		log.debug("└──────────────────────────────────────────┘");
		
		User newUser01 = users.get(0);
		
		MockHttpServletRequestBuilder requestBuilder = 
				MockMvcRequestBuilders.get("/user/doDelete.do")
				.param("userId", newUser01.getUserId());
		
		ResultActions resultActions = mockMvc.perform(requestBuilder)
										.andExpect(MockMvcResultMatchers.content()
										.contentType("text/plain;charset=UTF-8"))
										.andExpect(status().isOk());
		
		String jsonResult = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ jsonResult : " + jsonResult);
		log.debug("└──────────────────────────────────────────┘");
		
		Message resultMessage = new Gson().fromJson(jsonResult, Message.class);
		assertEquals(1, resultMessage.getMessageId());
		assertEquals(newUser01.getUserId()+"님의 삭제에 성공하셨습니다.", resultMessage.getMessageContents());
	}
	
	
	@Ignore
	@Test
	public void doSelectOne() throws Exception	{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ UserControllerTest : doSelectOne()       │");
		log.debug("└──────────────────────────────────────────┘");

		User newUser01 = users.get(0);
		
		MockHttpServletRequestBuilder requestBuilder = 
				MockMvcRequestBuilders.get("/user/doSelectOne.do")
				.param("userId", newUser01.getUserId());
		
		ResultActions resultActions = mockMvc.perform(requestBuilder)
										.andExpectAll(MockMvcResultMatchers.content()
										.contentType("text/plain;charset=UTF-8"))
										.andExpect(status().isOk());
		
		String jsonResult = resultActions.andDo(print()).andReturn()
										.getResponse().getContentAsString();
		
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ jsonResult : " + jsonResult);
		log.debug("└──────────────────────────────────────────┘");
		
		Message resultMessage = new Gson().fromJson(jsonResult, Message.class);
		assertEquals(1, resultMessage.getMessageId());
		assertEquals(newUser01.getUserId()+"님의 조회가 성공했습니다.", resultMessage.getMessageContents());
		
	}
	
	@Ignore
	@Test
	public void doSave() throws Exception{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ UserControllerTest : doSave()            │");
		log.debug("└──────────────────────────────────────────┘");
		//1. url 호출,파라미터 전달
		//2. return
		//3. 비교
		
		User newUser01 = users.get(0);
		
		//1. 
		MockHttpServletRequestBuilder requestBuilder = 
				MockMvcRequestBuilders.post("/user/doSave.do")
				.param("userId", newUser01.getUserId())
				.param("name", newUser01.getName())
				.param("password", newUser01.getPassword())
				.param("birthday", newUser01.getBirthday())
				.param("level", newUser01.getLevel()+"")
				.param("login", newUser01.getLogin()+"")
				.param("recommend", newUser01.getRecommend()+"")
				.param("email", newUser01.getEmail());
		//호출 및 결과
		ResultActions resultActions = mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
				//Controller produces = "text/plain;charset=UTF-8"
				//web 상태
				.andExpect(status().is2xxSuccessful());
		String jsonResult = resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
		
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ jsonResult : " + jsonResult);
		log.debug("└──────────────────────────────────────────┘");
		
		Message resultMessage = new Gson().fromJson(jsonResult, Message.class);
		
		assertEquals(1, resultMessage.getMessageId());
		assertEquals(newUser01.getUserId() + "님이 등록에 성공하셨습니다.", resultMessage.getMessageContents());
		
	}
	
	@Ignore
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                      			  │");
		log.debug("└──────────────────────────────────────────┘");
		
		log.debug("┌");
		log.debug("│ webApplicationContext : " + webApplicationContext);
		log.debug("└");
		
		assertNotNull(webApplicationContext);
	}

}
