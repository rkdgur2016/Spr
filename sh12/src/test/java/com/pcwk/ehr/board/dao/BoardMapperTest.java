package com.pcwk.ehr.board.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pcwk.ehr.board.domain.Board;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.mapper.BoardMapper;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations= {"classpath:/applicationContext.xml"})
public class BoardMapperTest implements PLog{

	@Autowired
	ApplicationContext context;
	
	@Autowired
	BoardMapper boardMapper;
	
	Board board01;
	Board board02;
	Board board03;
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ setUp()                            	  │");
		log.debug("└──────────────────────────────────────────┘");	
		
		board01 = new Board(1, "10", "제목_01", "내용_01", 0, "rkdgur2016", "sysdate", "rkdgur2016", "sysdate");
		board02 = new Board(2, "10", "제목_02", "내용_02", 0, "rkdgur2016", "sysdate", "rkdgur2016", "sysdate");
		board03 = new Board(3, "10", "제목_03", "내용_03", 0, "rkdgur2016", "sysdate", "rkdgur2016", "sysdate");
		
		boardMapper.deleteAll();
		
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ tearDown()                            	  │");
		log.debug("└──────────────────────────────────────────┘");	
	}
	
	@Test
	public void doSave() throws Exception {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ Save()	                            	  │");
		log.debug("└──────────────────────────────────────────┘");
		
		//첫번째 저장
		int flag = boardMapper.doSave(board01);
		assertEquals(1, flag);
		
		int seq = boardMapper.getSequence();
		log.debug("seq : " + seq);
		board01.setSeq(seq);
		
		Board outVO01 = boardMapper.doSelectOne(board01);
		log.debug("outVO01 : " + outVO01);
		assertNotNull(outVO01);
		
		//두번째 저장
		flag = boardMapper.doSave(board02);
		assertEquals(1, flag);
		
		seq = boardMapper.getSequence();
		log.debug("seq : " + seq);
		board02.setSeq(seq);
		
		Board outVO02 = boardMapper.doSelectOne(board02);
		log.debug("outVO02 : " + outVO02);
		assertNotNull(outVO02);
		
		//세번째 저장
		flag = boardMapper.doSave(board03);
		assertEquals(1, flag);
		
		seq = boardMapper.getSequence();
		log.debug("seq : " + seq);
		board03.setSeq(seq);
		
		Board outVO03 = boardMapper.doSelectOne(board03);
		log.debug("outVO03 : " + outVO03);
		assertNotNull(outVO03);
		
		isSameBoard(board03, outVO03);
	}
	
	private void isSameBoard(Board boardIn, Board boardOut) {

		assertEquals(boardIn.getSeq(), boardOut.getSeq());
		assertEquals(boardIn.getDiv(), boardOut.getDiv());
		assertEquals(boardIn.getTitle(), boardOut.getTitle());
		assertEquals(boardIn.getContents(), boardOut.getContents());
		assertEquals(boardIn.getReadCnt(), boardOut.getReadCnt());	
		
	}
	 
	@Ignore 
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                            	  │");
		log.debug("└──────────────────────────────────────────┘");	
		
		log.debug("context : " + context);
		log.debug("boardMapper : " + boardMapper);
		
		assertNotNull(context);
		assertNotNull(boardMapper);
	}

}
