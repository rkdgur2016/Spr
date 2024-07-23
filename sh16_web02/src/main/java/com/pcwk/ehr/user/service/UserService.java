package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.domain.User;

public interface UserService {

	int idDuplicateCheck(User inVO) throws SQLException;
	/**
	 * 최초 가입시 Level이 Null이면 BASIC
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	int doSave(User user) throws SQLException;

	/**
	 * 전체 회원 등업
	 * 
	 * @throws SQLException
	 */
	void upgradeLevelsTx() throws SQLException;// upgradeLevels end

	/**
	 * 단건 삭제
	 * 
	 * @param user
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	int doDelete(User inVO) throws SQLException;

	/**
	 * 목록 조회
	 */
	List<User> doRetrieve(DTO search) throws SQLException;

	/**
	 * 회원 수정
	 * 
	 * @param user
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	int doUpdate(User inVO) throws SQLException;

	/**
	 * 회원 정보 단건 조회
	 * 
	 * @param User
	 * @return User
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	User doSelectOne(User inVO) throws SQLException, NullPointerException;

}