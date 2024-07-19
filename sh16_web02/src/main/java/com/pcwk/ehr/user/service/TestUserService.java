package com.pcwk.ehr.user.service;

import java.sql.SQLException;

import com.pcwk.ehr.user.domain.User;

/**
 * 트랙잭션 테스트 : 특정 User가 들어오면 예외 발생! 모두 rollback
 * 
 * @author Jinseo
 *
 */
public class TestUserService extends UserServiceImpl {

	private String id;

	public TestUserService(String id) {
		this.id = id;
	}

	@Override
	protected void upgradeLevel(User user) throws SQLException {
		// 특정 ID가 들어오면 예외 발생
		if (id.equals(user.getUserId())) {
			throw new TestUserServiceException(id + "에서 예외 발생!");
		}

		super.upgradeLevel(user);
	}

}