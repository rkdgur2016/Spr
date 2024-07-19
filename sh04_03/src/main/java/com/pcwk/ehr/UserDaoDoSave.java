package com.pcwk.ehr;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDaoDoSave implements StatementStrategy {

	final Logger log = LogManager.getLogger(getClass());
	
	private User user;
	
	public UserDaoDoSave() {
		
	}
	
	public UserDaoDoSave(User user) {
		this.user = user;
	}
	
	@Override
	public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO MEMBER VALUES(?, ?, ?, ?) \n");
		log.debug("2. SQL : \n : " + sb.toString());
		
		PreparedStatement pstmt = conn.prepareStatement(sb.toString()); //conn.prepareStatement의 리턴 값은 PreparedStatement
		
		pstmt.setString(1, user.getUserId());
		pstmt.setString(2, user.getName());
		pstmt.setString(3, user.getPassword());
		pstmt.setString(4, user.getBirthday());
		
		return pstmt;
	}

}
