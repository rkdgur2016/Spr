package com.pcwk.ehr.board.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

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
import com.pcwk.ehr.cmn.Search;
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
	
	Search search;
	
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ setUp()                            	  │");
		log.debug("└──────────────────────────────────────────┘");	
		
		board01 = new Board(1, "10", "제목_01", "내용_01", 0, "rkdgur2016", "sysdate", "rkdgur2016", "sysdate");
		board02 = new Board(2, "10", "제목_02", "내용_02", 0, "rkdgur2016", "sysdate", "rkdgur2016", "sysdate");
		board03 = new Board(3, "10", "제목_03", "내용_03", 0, "rkdgur2016", "sysdate", "rkdgur2016", "sysdate");
		
		search = new Search();
		
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ tearDown()                            	  │");
		log.debug("└──────────────────────────────────────────┘");	
	}
	
	@Test
	public void addAndGet() throws Exception {
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
		
		//삭제
		flag = boardMapper.doDelete(outVO01);
		assertEquals(1, flag);
		
		
	}
	
	private void isSameBoard(Board boardIn, Board boardOut) {

		assertEquals(boardIn.getSeq(), boardOut.getSeq());
		assertEquals(boardIn.getDiv(), boardOut.getDiv());
		assertEquals(boardIn.getTitle(), boardOut.getTitle());
		assertEquals(boardIn.getContents(), boardOut.getContents());
		assertEquals(boardIn.getReadCnt(), boardOut.getReadCnt());	
		
	}
	
	@Test
	public void doRetrieve() throws SQLException {
		
		boardMapper.deleteAll();
		
		int flag = boardMapper.multipleSave();
		assertEquals(101, flag);
		
		search.setPageNo(1);
		search.setPageSize(10);
		
		search.setSearchDiv("10");
		search.setSearchWord("제목000008");
		List<Board> list = boardMapper.doRetrieve(search);
		assertEquals(10, list.size());
	}
	
	@Test
	public void doUpdate() throws SQLException	{
		
		int flag = boardMapper.doSave(board01);
		assertEquals(1, flag);
		
		int seq = boardMapper.getSequence();
		log.debug("seq : " + seq);
		board01.setSeq(seq);
		
		Board outVO01 = boardMapper.doSelectOne(board01);
		log.debug("outVO01 : " + outVO01);
		assertNotNull(outVO01);

		isSameBoard(board01, outVO01);
		
		Board outVO = boardMapper.doSelectOne(outVO01); 
		//업데이트
		String updateStr = "_Ultra";
		outVO.setDiv("20");
		outVO.setTitle(outVO.getTitle() + updateStr);
		outVO.setContents(outVO.getContents() + updateStr);
		outVO.setReadCnt(1);
		outVO.setModId(outVO.getModId() + updateStr);
		
		flag = boardMapper.doUpdate(outVO);
		log.debug("flag : " + flag);
		assertEquals(1, flag);
		
		Board outVOUpdate = boardMapper.doSelectOne(outVO01);
		log.debug("outVOUpdate : " + outVOUpdate);
		assertNotNull(outVOUpdate);
		
		isSameBoard(outVOUpdate, outVO);
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
