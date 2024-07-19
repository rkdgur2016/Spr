package com.pcwk.ehr;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class ConnectionConnecter {
	
	//드라이버, URL, USER_ID, PASSWORD
	final	static	String	DB_DRIVER	= "oracle.jdbc.driver.OracleDriver";
	final	static	String	DB_URL 		= "jdbc:oracle:thin:@192.168.0.71:1521:xe";
	final	static	String  DB_USER_ID 	= "scott";
	final	static	String  DB_PASSWORD = "pcwk";

	public ConnectionConnecter() {
		System.out.println("connectionConnecter()");	
	}
	
	public Connection getConnection() {
		
		Connection conn = null;
		
		try {
			Class.forName(DB_DRIVER);
			
			conn = DriverManager.getConnection(DB_URL,DB_USER_ID,DB_PASSWORD);
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
