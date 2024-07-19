package com.pcwk.ehr.user.service;

import java.sql.SQLException;

import com.pcwk.ehr.user.domain.User;

public interface UserService {

	/**
	 * 최초가입시 level이 null이면 basic
	 * @param user
	 * return
	 * throws SQLException
	 */

	int doSave(User user) throws SQLException;

	/**
	 * 전체 회원 등업
	 * @throws SQLException 
	 */
	void upgradeLevelsTx() throws SQLException;

}