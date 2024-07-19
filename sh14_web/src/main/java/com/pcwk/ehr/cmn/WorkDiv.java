package com.pcwk.ehr.cmn;

import java.sql.SQLException;

import java.util.List;

import com.pcwk.ehr.board.domain.Board;

public interface WorkDiv<T>{
	
	int doSave(T inVO) throws SQLException;

	T doSelectOne(T inVO) throws SQLException, NullPointerException;
	
	List<T> doRetrieve(DTO search) throws SQLException;
	
	int doUpdate(T inVO) throws SQLException;
	
	int doDelete(T inVO) throws SQLException;
}
