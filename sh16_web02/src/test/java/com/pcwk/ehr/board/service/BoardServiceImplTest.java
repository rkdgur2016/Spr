package com.pcwk.ehr.board.service;

import static org.junit.Assert.*;

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

@RunWith(SpringRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //알파벳 순서로 테스트 메서드 실행
public class BoardServiceImplTest implements PLog{
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	BoardMapper boardMapper;
	
	Board board01;
	
	//@Ignore
	@Test
	public void doSelectOne() throws Exception {
		log.debug("┌──────────────────────────────────────┐");
		log.debug("│ doSelectOne()                        │");
		log.debug("└──────────────────────────────────────┘");
		
		//1. 데이터 입력
		//1.1
		//2. 단건조회
		
		//1
		int flag = boardMapper.doSave(board01);
		log.debug("flag : " + flag);
		assertEquals(1, flag);
		
		// 등록 seq조회 : 등록 당시에는 SEQ를 알지 못함
		int seq = boardMapper.getSequence();
		log.debug("seq : " + seq);
		board01.setSeq(seq);
		
		//등록자 ID가 같은 경우 조회 count증가 안됨!
		board01.setRegId(board01.getRegId() + "U");
		
		// 단건 조회
		Board outVO01 = this.boardService.doSelectOne(board01);
		
		//read_cnt++
		outVO01.setReadCnt(outVO01.getReadCnt() - 1);
		
		isSameBoard(outVO01, board01);
		
	}
	
	public void isSameBoard(Board boardIn, Board boardOut) {
		assertEquals(boardIn.getSeq(), boardOut.getSeq());
		assertEquals(boardIn.getDiv(), boardOut.getDiv());
		assertEquals(boardIn.getTitle(), boardOut.getTitle());
		assertEquals(boardIn.getContents(), boardOut.getContents());
		assertEquals(boardIn.getReadCnt(), boardOut.getReadCnt());
		//assertEquals(boardIn.getRegId(), boardOut.getRegId());
		assertEquals(boardIn.getModId(), boardOut.getModId());
	}
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌──────────────────────────────────────┐");
		log.debug("│ setUp()                              │");
		log.debug("└──────────────────────────────────────┘");
		
		//전체 삭제
		boardService.deleteAll();
		
		board01 = new Board(1, "10", "제목_01", "내용_01", 0, "admin", "사용안함", "admin", "사용안함");
		
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌──────────────────────────────────────┐");
		log.debug("│ tearDown()                           │");
		log.debug("└──────────────────────────────────────┘");
		
		
	}
	
	

	@Ignore
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────┐");
		log.debug("│ beans()                              │");
		log.debug("└──────────────────────────────────────┘");
		log.debug("context:" + context);
		log.debug("boardService:" + boardService);
		log.debug("boardMapper:" + boardMapper);
		
		assertNotNull(context);
		assertNotNull(boardService);
		assertNotNull(boardMapper);
	}

}
