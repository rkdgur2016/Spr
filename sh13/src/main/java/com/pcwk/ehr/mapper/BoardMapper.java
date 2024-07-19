package com.pcwk.ehr.mapper;

import java.sql.SQLException;

import com.pcwk.ehr.board.domain.Board;
import com.pcwk.ehr.cmn.WorkDiv;

public interface BoardMapper extends WorkDiv<Board>{

	int readCntUpdate(Board inVO) throws SQLException;
	
	int deleteAll() throws SQLException;
	
	int getSequence() throws SQLException;
	
	int multipleSave() throws SQLException;
}
