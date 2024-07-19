package com.pcwk.ehr.user.service;

import java.sql.SQLException;

import com.pcwk.ehr.user.domain.User;

/**
 * 트랜잭션 테스트: 특정 User가 들어오면 예외발생!
 * 모두 rollback;
 * @author acorn
 *
 */
public class TestUserService extends UserService {
	
	private String id;
	
	public TestUserService(String id) {
		this.id = id;
	}
	
	@Override
	protected void upgradeLevel(User user) throws SQLException {
		
		//특정ID
		
		if(id.equals(user.getUserId())) {
			throw new TestUserServiceException(id+"예외발생");
		}
	}
}
