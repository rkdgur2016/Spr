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
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.User;

@RunWith(SpringRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 알파벳 순서대로 메서드 실행
public class BoardServiceImplTest implements PLog{

	@Autowired
	ApplicationContext context;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	BoardMapper boardMapper;
	
	Board board01;
	Board board02;
	Board board03;
	
	public void isSameBoard(Board boardIn, Board boardOut) {
		assertEquals(boardIn.getSeq(), boardOut.getSeq());
		assertEquals(boardIn.getDiv(), boardOut.getDiv());
		assertEquals(boardIn.getTitle(), boardOut.getTitle());
		assertEquals(boardIn.getContents(), boardOut.getContents());
		assertEquals(boardIn.getReadCnt(), boardOut.getReadCnt());
		assertEquals(boardIn.getModId(), boardOut.getModId());
	}	
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ setUp()");
		log.debug("└─────────────────────────────────────────────────────────");
	
		// 0. 전체 삭제
		boardMapper.deleteAll();
		
		board01 = new Board(1, "10", "제목01", "내용01", 0, "ADMIN01", "사용안함", "ADMIN01", "사용안함");
		board02 = new Board(2, "10", "제목02", "내용02", 0, "ADMIN02", "사용안함", "ADMIN02", "사용안함");
		board03 = new Board(3, "10", "제목03", "내용03", 0, "ADMIN03", "사용안함", "ADMIN03", "사용안함");
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ tearDown()");
		log.debug("└─────────────────────────────────────────────────────────");
	}
	
	@Test
	public void doSelectOne() throws Exception{
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ doSelectOne()");
		log.debug("└─────────────────────────────────────────────────────────");
		
		// 1. 데이터 저장
		int flag = boardMapper.doSave(board01);
		log.debug("flag : " + flag);
		assertEquals(1, flag);
		
		// 2. seq 조회 후 설정
		int seq = boardMapper.getSequence();
		log.debug("seq" + seq);
		board01.setSeq(seq);
		
		// 등록자 ID가 같은경우
		board01.setRegId(board01.getRegId() + "U");		
		
		// 3. 단건 조회
		Board outVO01 = this.boardService.doSelectOne(board01);
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ outVO01 " + outVO01);
		log.debug("└─────────────────────────────────────────────────────────");
		assertNotNull(outVO01);
		
		// 4. 비교(read_cnt++)
		outVO01.setReadCnt(outVO01.getReadCnt() - 1);
		isSameBoard(outVO01, board01);		
	}

	@Ignore
	@Test
	public void test() {
		log.debug("┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰");
		log.debug("━━━━━━━━━━━━━━━beans━━━━━━━━━━━━━━━");
		log.debug("context : " + context);
		log.debug("boardService : " + boardService);
		log.debug("boardMapper : " + boardMapper);
		log.debug("┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻");
		assertNotNull(context);
		assertNotNull(boardService);
		assertNotNull(boardMapper);
	}

}
