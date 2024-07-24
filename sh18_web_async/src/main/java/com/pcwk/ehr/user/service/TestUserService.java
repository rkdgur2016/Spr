package com.pcwk.ehr.user.service;

import java.sql.SQLException;

import com.pcwk.ehr.user.domain.User;

/*
 * 트랜잭션 테스트 : 특정 User가 들어오면 예외 발생!
 * 모두 rollback;
 * @author acron
 * */
public class TestUserService extends UserServiceImpl {
	// 예외가 발생한다면 롤백을 실행한다
	private String id;

	public TestUserService(String id) {
		this.id = id;
	}

	@Override
	protected void upgradeLevel(User user) throws SQLException {

		// 특정 ID가 들어오면 예외 발생
		if (id.equals(user.getUserId())) {
			throw new TestUserServiceException(id + " 예외다 나가~");
		}
		
		super.upgradeLevel(user);
	}
	
	
	
}
