package com.pcwk.ehr.board.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.pcwk.ehr.board.domain.Board;
import com.pcwk.ehr.cmn.Message;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.mapper.BoardMapper;
import com.pcwk.ehr.user.domain.User;

@WebAppConfiguration
@RunWith(SpringRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class BoardControllerTest implements PLog{

	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	BoardMapper boardMapper;
	
	MockMvc mockMvc;
	
	Board board01;
	Board board02;
	Board board03;
	
	Search search;
	
	static class Response {
		Board board;
		Message message;
	}
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ setUp()");
		log.debug("└─────────────────────────────────────────────────────────");
		
		boardMapper.deleteAll();
		
		board01 = new Board(1, "10", "제목01", "내용01", 0, "ADMIN01", "사용안함", "ADMIN01", "사용안함");
		board02 = new Board(2, "10", "제목02", "내용02", 0, "ADMIN02", "사용안함", "ADMIN02", "사용안함");
		board03 = new Board(3, "10", "제목03", "내용03", 0, "ADMIN03", "사용안함", "ADMIN03", "사용안함");
		
		search = new Search();
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ tearDown()");
		log.debug("└─────────────────────────────────────────────────────────");
	}
	@Test
	public void doUpdate() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ doUpdate()");
		log.debug("└─────────────────────────────────────────────────────────");
		
		int flag = boardMapper.doSave(board01);
		log.debug("1, flag : " + flag);
		assertEquals(1, flag);
		
		int seq = boardMapper.getSequence();
		log.debug("seq : " + seq);
		board01.setSeq(seq);
		
		Board outVO = boardMapper.doSelectOne(board01);
		
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/board/doUpdate.do")
				.param("seq", outVO.getSeq() + "")
				.param("div", outVO.getDiv())
				.param("title", outVO.getTitle()+ "_U")
				.param("contents", outVO.getContents()+ "_U")
				.param("modId", outVO.getModId())
				;
		
		ResultActions resultActions = mockMvc.perform(requestBuilder)
				// Controller produces = "text/plain;charset=UTF-8"
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
				// Web상태
				.andExpect(status().is2xxSuccessful());
		
		String jsonResult = resultActions.andDo(print())
				.andReturn()
				.getResponse()
				.getContentAsString()
				;
		
		log.debug("┌────────────────────────────────────────────────────────");
		log.debug("│ jsonResult() : " + jsonResult);
		log.debug("└────────────────────────────────────────────────────────");
		// json 문자열을 Message로 변환
		Message resultMessage = new Gson().fromJson(jsonResult, Message.class);
		
		flag = boardMapper.doUpdate(outVO);
		
		assertEquals(1, flag);
	}
	
	@Ignore
	@Test
	public void doRetrieve() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ doRetrieve()");
		log.debug("└─────────────────────────────────────────────────────────");
		
		boardMapper.multipleSave();
		
		search.setPageNo(1);
		search.setPageSize(10);
		
		MockHttpServletRequestBuilder requestBuilder = 
				MockMvcRequestBuilders.get("/board/doRetrieve.do")
				.param("searchDiv", search.getSearchDiv())
				.param("searchWord",search.getSearchWord())
				.param("pageSize",search.getPageSize()+"")
				.param("pageNo",search.getPageNo()+"")
				;
		
		ResultActions resultActions = mockMvc.perform(requestBuilder)
				.andExpect(status().is2xxSuccessful());
		
		MvcResult mvcResult = resultActions.andDo(print()).andReturn();
		
		Map<String, Object> modelMap = mvcResult.getModelAndView().getModel();
	
		List<Board> list = (List<Board>)modelMap.get("list");
		for(Board vo : list) {
			log.debug(vo);
		}
		int totalCnt = (Integer)modelMap.get("totalCnt");
		String viewName = mvcResult.getModelAndView().getViewName();
		
		assertEquals(101, totalCnt);
		assertEquals("board/board_list",viewName);
	}
	
	@Ignore
	@Test
	public void doDelete() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ doDelete()");
		log.debug("└─────────────────────────────────────────────────────────");
		
		int flag = boardMapper.doSave(board01);
		log.debug("1, flag : " + flag);
		assertEquals(1, flag);
		
		int seq = boardMapper.getSequence();
		log.debug("seq : " + seq);
		board01.setSeq(seq);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/board/doDelete.do")
				.param("seq", board01.getSeq() + "")
				;
		
		ResultActions resultActions = mockMvc.perform(requestBuilder)
				// Controller produces = "text/plain;charset=UTF-8"
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
				// Web상태
				.andExpect(status().is2xxSuccessful());
		
		String jsonResult = resultActions.andDo(print())
				.andReturn()
				.getResponse()
				.getContentAsString()
				;
		
		log.debug("┌────────────────────────────────────────────────────────");
		log.debug("│ jsonResult() : " + jsonResult);
		log.debug("└────────────────────────────────────────────────────────");
		// json 문자열을 Message로 변환
		
		Message resultMessage = new Gson().fromJson(jsonResult, Message.class);
		
		assertEquals(1, resultMessage.getMessageId());
		assertEquals(board01.getSeq() + "이 삭제되었습니다.", resultMessage.getMessageContents());
		
	}
	
	@Ignore
	@Test
	public void doSelectOne() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ doSelectOne()");
		log.debug("└─────────────────────────────────────────────────────────");
		
		int flag = boardMapper.doSave(board01);
		log.debug("1, flag : " + flag);
		assertEquals(1, flag);
		
		int seq = boardMapper.getSequence();
		log.debug("seq : " + seq);
		board01.setSeq(seq);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/board/doSelectOne.do")
				.param("seq", board01.getSeq() + "")
				.param("regId",board01.getRegId())
				;
		
		ResultActions resultActions = mockMvc.perform(requestBuilder)
				// Controller produces = "text/plain;charset=UTF-8"
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
				// Web상태
				.andExpect(status().is2xxSuccessful());
		
		String jsonResult = resultActions.andDo(print())
				.andReturn()
				.getResponse()
				.getContentAsString()
				;
		
		log.debug("┌────────────────────────────────────────────────────────");
		log.debug("│ jsonResult() : " + jsonResult);
		log.debug("└────────────────────────────────────────────────────────");
		// json 문자열을 Message로 변환
		Gson gson = new Gson();
		
		Response response = gson.fromJson(jsonResult, Response.class);
		
		Message message = response.message;
		
		Board board = response.board;
		
		log.debug("Message : " + message);
		log.debug("Board : " + board);
		
		assertEquals(1, message.getMessageId());
		assertEquals(board01.getTitle() + "이 조회되었습니다.", message.getMessageContents());
		
		isSameBoard(board, board01);
	}
	
	@Ignore
	@Test
	public void doSave() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ doSave()");
		log.debug("└─────────────────────────────────────────────────────────");
		
	
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/board/doSave.do")
				.param("div", board01.getDiv())
				.param("title", board01.getTitle())
				.param("contents", board01.getContents())
				.param("regId", board01.getRegId())
				;
		
		ResultActions resultActions = mockMvc.perform(requestBuilder)
				// Controller produces = "text/plain;charset=UTF-8"
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
				// Web상태
				.andExpect(status().is2xxSuccessful());
		
		String jsonResult = resultActions.andDo(print())
				.andReturn()
				.getResponse()
				.getContentAsString()
				;
		

		log.debug("┌────────────────────────────────────────────────────────");
		log.debug("│ jsonResult() : " + jsonResult);
		log.debug("└────────────────────────────────────────────────────────");
		// json 문자열을 Message로 변환
		Message resultMessage = new Gson().fromJson(jsonResult, Message.class);
		
		// 3. 비교
		assertEquals(1, resultMessage.getMessageId());
		assertEquals(board01.getTitle() + "을 등록했습니다.", resultMessage.getMessageContents());
	}
	@Ignore
	@Test
	public void beans() {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ beans()");
		log.debug("└─────────────────────────────────────────────────────────");
		log.debug("┌");
		log.debug("│ webApplicationContext : " + webApplicationContext);
		log.debug("│ mockMvc : " + mockMvc);
		log.debug("└");
		assertNotNull(webApplicationContext);
		assertNotNull(mockMvc);
	}

	public void isSameBoard(Board boardIn, Board boardOut) {
		assertEquals(boardIn.getSeq(), boardOut.getSeq());
		assertEquals(boardIn.getDiv(), boardOut.getDiv());
		assertEquals(boardIn.getTitle(), boardOut.getTitle());
		assertEquals(boardIn.getContents(), boardOut.getContents());
		assertEquals(boardIn.getReadCnt(), boardOut.getReadCnt());
		assertEquals(boardIn.getRegId(), boardOut.getRegId());
		assertEquals(boardIn.getModId(), boardOut.getModId());
	}	
}
