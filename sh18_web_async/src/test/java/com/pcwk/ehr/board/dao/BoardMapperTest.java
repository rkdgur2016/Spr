package com.pcwk.ehr.board.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
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

import com.pcwk.ehr.board.domain.Board;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.code.domain.Code;
import com.pcwk.ehr.mapper.BoardMapper;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

@RunWith(SpringRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 알파벳 순서대로 메서드 실행
public class BoardMapperTest implements PLog{

	@Autowired
	ApplicationContext context;
	
	@Autowired 
	BoardMapper boardMapper;
	
	Board board01;
	Board board02;
	Board board03;
	Search 	search;
	
	public void isSameUser(Board boardIn, Board boardOut) {
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
		
		board01 = new Board(1, "10", "제목01", "내용01", 0, "ADMIN01", "사용안함", "ADMIN01", "사용안함");
		board02 = new Board(2, "10", "제목02", "내용02", 0, "ADMIN02", "사용안함", "ADMIN02", "사용안함");
		board03 = new Board(3, "10", "제목03", "내용03", 0, "ADMIN03", "사용안함", "ADMIN03", "사용안함");
		
		boardMapper.deleteAll();
		search = new Search();
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ tearDown()");
		log.debug("└─────────────────────────────────────────────────────────");
	}

	@Test
	public void doRetrieve() throws SQLException {
		// 1. 전체삭제 (set에서 실행)
		
		// 2. 101개 DB생성 및 확인
		boardMapper.multipleSave();
		
		search.setPageNo(1);
		search.setPageSize(10);
		
		// 상무
		search.setSearchDiv("10");
		search.setSearchWord("");
		
		List<Board> list = boardMapper.doRetrieve(search);
		assertEquals(10, list.size());
	}	

	@Test
	public void doUpdate() throws SQLException{
		// 1. 전체삭제 (setUp에 존재)
		
		// 2. 데이터 1건 입력
		int flag = boardMapper.doSave(board01);		
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ doSave(flag) : " + flag);
		log.debug("└─────────────────────────────────────────────────────────");
		assertEquals(1, flag);
		
		// 2_1. 시퀀스 세팅
		int seq = boardMapper.getSequence();
		log.debug("seq" + seq);
		board01.setSeq(seq);	
		
		// 3. 단건 조회
		Board outVO01 = boardMapper.doSelectOne(board01);
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ doSelectOne(flag) : " + flag);
		log.debug("└─────────────────────────────────────────────────────────");
		assertNotNull(outVO01);
		isSameUser(board01, outVO01);
		
		// 4. 조회 데이터로 데이터 수정 및 Update
		String updateStr = "_U";
		
		outVO01.setDiv("20");
		outVO01.setTitle(outVO01.getTitle()+updateStr);
		outVO01.setContents(outVO01.getContents()+updateStr);
		outVO01.setReadCnt(outVO01.getReadCnt());
		outVO01.setRegId(outVO01.getRegId()+updateStr);
		outVO01.setSeq(outVO01.getSeq());
		
		flag = boardMapper.doUpdate(outVO01);		
		assertEquals(1, flag);
		
		// 5. 수정된데이터 조회
		Board upOutVO = boardMapper.doSelectOne(outVO01);

		// 6. 비교	
		isSameUser(upOutVO, outVO01);		
	}

	@Test
	public void addAndGet() throws SQLException {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ addAndGet()");
		log.debug("└─────────────────────────────────────────────────────────");
		
		// 1. 전체 삭제 (setUp에서 발생)
		// 1.2 단건삭제
		int flag = boardMapper.doDelete(board01);
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ doDelete(flag) : " + flag);
		log.debug("└─────────────────────────────────────────────────────────");
		flag = boardMapper.doDelete(board02);
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ doDelete(flag) : " + flag);
		log.debug("└─────────────────────────────────────────────────────────");
		flag = boardMapper.doDelete(board03);
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ doDelete(flag) : " + flag);
		log.debug("└─────────────────────────────────────────────────────────");
		
		// 2_1. 내용 insert 1건
		flag = boardMapper.doSave(board01);
		log.debug("flag : " + flag);
		assertEquals(1, flag);
		
		int seq = boardMapper.getSequence();
		log.debug("seq" + seq);
		board01.setSeq(seq);
		
		// 단건 조회
		Board outVO01 = boardMapper.doSelectOne(board01);
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ outVO01 " + outVO01);
		log.debug("└─────────────────────────────────────────────────────────");
		assertNotNull(outVO01);
		isSameUser(board01, outVO01);
		
		// 2_2. 내용 insert 2건
		flag = boardMapper.doSave(board02);
		log.debug("flag : " + flag);
		assertEquals(1, flag);

		seq = boardMapper.getSequence();
		log.debug("seq" + seq);
		board02.setSeq(seq);
		
		// 단건 조회
		Board outVO02 = boardMapper.doSelectOne(board02);
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ outVO01 " + outVO02);
		log.debug("└─────────────────────────────────────────────────────────");
		assertNotNull(outVO02);
		isSameUser(board02, outVO02);
		
		// 2_3. 내용 insert 3건
		flag = boardMapper.doSave(board03);
		log.debug("flag : " + flag);
		assertEquals(1, flag);
		
		seq = boardMapper.getSequence();
		log.debug("seq" + seq);
		board03.setSeq(seq);
		
		// 단건 조회
		Board outVO03 = boardMapper.doSelectOne(board03);
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ outVO01 " + outVO03);
		log.debug("└─────────────────────────────────────────────────────────");
		assertNotNull(outVO03);
		isSameUser(board03, outVO03);
	}
	
	@Test
	public void beans() {
		log.debug("┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰");
		log.debug("━━━━━━━━━━━━━━━beans━━━━━━━━━━━━━━━");
		log.debug("context : " + context);
		log.debug("┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻");
		assertNotNull(context);
	}

}
