package com.pcwk.ehr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NConnectionMaker implements ConnectionMaker{

	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		
		final	String	DB_DRIVER	= "oracle.jdbc.driver.OracleDriver";
		final	String	DB_URL 		= "jdbc:oracle:thin:@192.168.0.71:1521:xe";
		final	String  DB_USER_ID 	= "scott";
		final	String  DB_PASSWORD = "pcwk";
		
		Class.forName(DB_DRIVER);
		
		Connection conn = DriverManager.getConnection(DB_URL,DB_USER_ID,DB_PASSWORD);
		
		return conn;
	}

	
	
}
