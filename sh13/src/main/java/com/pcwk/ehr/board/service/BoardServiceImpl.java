package com.pcwk.ehr.board.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.board.domain.Board;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.mapper.BoardMapper;
import com.pcwk.ehr.user.domain.User;

@Service("boardServiceImpl")
public class BoardServiceImpl implements BoardService, PLog{

	@Autowired
	BoardMapper boardMapper;
	
	public BoardServiceImpl() {}
	
	@Override
	public int doSave(Board inVO) throws SQLException {
		log.debug("1. param : " + inVO);
		
		return this.boardMapper.doSave(inVO);
	}

	@Override
	public Board doSelectOne(Board inVO) throws SQLException, NullPointerException {
		//단건 조회
		log.debug("1. param : " + inVO);
		Board outVO	 = boardMapper.doSelectOne(inVO);
		
		log.debug("2. outVO : " + outVO);
		
		//조회 카운트 증가
		int flag = 0;
		
		if(null != outVO) {
			flag = boardMapper.readCntUpdate(inVO);
			log.debug("3. 조회 count 증가 : " + flag);
			
			if(flag == 1) {
				outVO.setReadCnt(outVO.getReadCnt()+1);
			}
			
		}
		
		return outVO;
	}

	@Override
	public List<Board> doRetrieve(DTO search) throws SQLException {
		log.debug("1. param : " + search);
		
		return this.boardMapper.doRetrieve(search);
	}

	@Override
	public int doUpdate(Board inVO) throws SQLException {
		log.debug("1. param : " + inVO);
		return this.boardMapper.doUpdate(inVO);
		
	}

	@Override
	public int doDelete(Board inVO) throws SQLException {
		log.debug("1. param : " + inVO);
		int flag = this.boardMapper.doDelete(inVO);
		
		log.debug("2. flag : " + flag);
		return flag;
	}

	
}	
