package com.pcwk.ehr.cmn;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.user.domain.User;

public interface WorkDiv<T> {
	/*
	 * 회원 정보 단건 등록
	 * @param user
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 * */
	int doSave(T inVO) throws SQLException;

	// 단건 조회
	/*
	 * 회원 정보 단건 조건
	 * @param user
	 * @return User
	 * @SQLException
	 * @NullPointerException
	 * */
	T doSelectOne(T inVO) throws SQLException, NullPointerException;
	/*
	 * 목록조회
	 * */
	List<T> doRetrieve(DTO search) throws SQLException;
	/*
	 * 회원 수정
	 * @param user
	 * @return 1(성공) / 0(실패)
	 * @throws SQLException
	 * */
	int doUpdate(T inVO) throws SQLException;
	
	/*
	 * 단건 삭제
	 * @param user
	 * @return 1(성공) / 0(실패)
	 * */
	int doDelete(T inVO) throws SQLException;
}
