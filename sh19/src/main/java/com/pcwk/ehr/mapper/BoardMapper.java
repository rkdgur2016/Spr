package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.board.domain.Board;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.code.domain.Code;

@Mapper
public interface BoardMapper extends WorkDiv<Board> {
	
	/*
	 * 조회 카운트 증가
	 * @param inVO
	 * @return
	 * @throws SQLException
	 */
	int readCntUpdate(Board inVO) throws SQLException;
	
	/*
	 * 다건 데이터 등록
	 * @return
	 * @throws SQLException
	 */
	int multipleSave() throws SQLException;	
	
	/*
	 * 테스트용 전체 데이터 삭제
	 * @return
	 * @throws SQLException
	 * */
	int deleteAll() throws SQLException; // 테스트용 전체 데이터 삭제 끝
	
	/*
	 * 최신 sequence 조회
	 * @return
	 * @throws SQLException
	 * */
	int getSequence() throws SQLException;
}
