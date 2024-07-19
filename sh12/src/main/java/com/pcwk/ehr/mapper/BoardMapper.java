package com.pcwk.ehr.mapper;

import java.sql.SQLException;

import com.pcwk.ehr.board.domain.Board;
import com.pcwk.ehr.cmn.WorkDiv;

public interface BoardMapper extends WorkDiv<Board>{

	int deleteAll() throws SQLException;
	
	int getSequence() throws SQLException;
	
}
