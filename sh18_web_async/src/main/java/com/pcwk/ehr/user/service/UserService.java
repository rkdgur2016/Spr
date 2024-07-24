package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.domain.User;

public interface UserService {

	/**
	 * 아이디 중복 체크
	 * @param invo
	 * @return 1(중복) , 0(중복x)
	 * @throws SQLException
	 */
	int idDuplicateCheck(User inVO) throws SQLException;
	
	/*
	 * 최초 가입시
	 * @param user
	 * @return user
	 * @throws SQLException
	 * */
	int doSave(User user) throws SQLException;

	/*
	 * 전체 회원 등업
	 * @throws SQLException
	 * */
	void upgradeLevelsTx() throws SQLException;
	
	/*
	 * 단건 삭제
	 * @param user
	 * @return 1(성공) / 0(실패)
	 * */
	int doDelete(User inVO) throws SQLException;
	
	/*
	 * 목록조회
	 * */
	List<User> doRetrieve(DTO search) throws SQLException;
	
	/*
	 * 회원 수정
	 * @param user
	 * @return 1(성공) / 0(실패)
	 * @throws SQLException
	 * */
	int doUpdate(User inVO) throws SQLException;
	
	// 단건 조회
	/*
	 * 회원 정보 단건 조건
	 * @param user
	 * @return User
	 * @SQLException
	 * @NullPointerException
	 * */
	User doSelectOne(User inVO) throws SQLException, NullPointerException;
}