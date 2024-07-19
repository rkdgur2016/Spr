package com.pcwk.ehr.board.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import com.pcwk.ehr.board.domain.Board;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.mapper.BoardMapper;

@RunWith(SpringRunner.class) //스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
								   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //알파벳 순서로 테스트 메서드 실행
public class BoardServiceImplTest implements PLog {

	@Autowired
	ApplicationContext context;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	BoardMapper boardMapper;
	
	Board board01;
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		boardMapper.deleteAll();
		
		board01 = new Board(1, "10", "제목_01", "내용_01", 0, "rkdgur2016", "sysdate", "rkdgur2016", "sysdate");
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");		
	}
	
	
	@Test
	public void doSelectOne() throws Exception	{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ doSelectOne()                            │");
		log.debug("└──────────────────────────────────────────┘");
		
		int flag = boardMapper.doSave(board01);
		assertEquals(1, flag);
		
		int seq = boardMapper.getSequence();
		board01.setSeq(seq);
		//등록자 ID가 같은 경우 조회 count 증가 불가;
		board01.setRegId(board01.getRegId()+"U");
		
		log.debug("┌");
		log.debug("│flag : " + flag);
		log.debug("│seq : " + seq);
		log.debug("└");
		
		//단건 조회
		Board outVO01 = this.boardService.doSelectOne(board01);
		
		//read_cnt ++
		outVO01.setReadCnt(outVO01.getReadCnt()-1);
		
		isSameBoard(outVO01, board01);
		
	}
	
	private void isSameBoard(Board boardIn, Board boardOut) {

		assertEquals(boardIn.getSeq(), boardOut.getSeq());
		assertEquals(boardIn.getDiv(), boardOut.getDiv());
		assertEquals(boardIn.getTitle(), boardOut.getTitle());
		assertEquals(boardIn.getContents(), boardOut.getContents());
		assertEquals(boardIn.getReadCnt(), boardOut.getReadCnt());
		assertEquals(boardIn.getModId(), boardOut.getModId());
		
		log.debug("┌");
		log.debug("│ boardIn.getSeq() : {}", boardIn.getSeq());
		log.debug("│ boardIn.getDiv() : {}", boardIn.getDiv());
		log.debug("│ boardIn.getTitle() : {}", boardIn.getTitle());
		log.debug("│ boardIn.getContents() : {}", boardIn.getContents());
		log.debug("│ boardIn.getReadCnt() : {}",boardIn.getReadCnt());
		log.debug("│ boardIn.getModId() : {}", boardIn.getModId());
		log.debug("└");
		
		log.debug("┌");
		log.debug("│ boardOut.getSeq() : {}", boardOut.getSeq());
		log.debug("│ boardOut.getDiv() : {}", boardOut.getDiv());
		log.debug("│ boardOut.getTitle() : {}", boardOut.getTitle());
		log.debug("│ boardOut.getContents() : {}", boardOut.getContents());
		log.debug("│ boardOut.getReadCnt() : {}",boardOut.getReadCnt());
		log.debug("│ boardOut.getModId() : {}", boardOut.getModId());
		log.debug("└");
	}
	
	@Ignore
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                                  │");
		log.debug("└──────────────────────────────────────────┘");
		log.debug("┌");
		log.debug("│ context:" + context);
		log.debug("│ boardService:" + boardService);
		log.debug("│ boardMapper:" + boardMapper);
		log.debug("└");
		
		
		assertNotNull(context);
		assertNotNull(boardService);
		assertNotNull(boardMapper);
	}

}
