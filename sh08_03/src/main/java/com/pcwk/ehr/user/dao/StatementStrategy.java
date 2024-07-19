package com.pcwk.ehr.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
	
	public PreparedStatement makePreparedStatement(Connection conn) throws SQLException;
}
