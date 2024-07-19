package com.pcwk.ehr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	
	ConnectionConnecter ConnectionConnecter ;
	
	public UserDao(){
		ConnectionConnecter = new ConnectionConnecter();
		Connection connection = ConnectionConnecter.getConnection(); 
	}
	
	public int doSave(User user) throws SQLException, ClassNotFoundException{
		int flag = 0;
		//INSERT INTO MEMBER VALUES(:USER_ID, :NAME, :PASSWORD, :BIRTHDAY);
		
		//1. DB 연결을 위한 Connection
		//2. SQL을 담은 PreparedStatement, Statement를 생성
		//3. PreparedStatement를 실행한다.
		//4. 실행 결과 받기 ResultSet 받아서 저장
		//5. Connection. PreparedStatement, ResultSet 반환
		//6. JDBC API에 대한 예외 처리
		
		Connection conn = ConnectionConnecter.getConnection();
		System.out.println("1. conn : " + conn);
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO MEMBER VALUES(?, ?, ?, ?) \n");
		
		System.out.println("2. SQL : \n : " + sb.toString());
		System.out.println("3. User : \n : " + user);
		PreparedStatement pstmt = conn.prepareStatement(sb.toString()); //conn.prepareStatement의 리턴 값은 PreparedStatement
		
		pstmt.setString(1, user.getUserId());
		pstmt.setString(2, user.getName());
		pstmt.setString(3, user.getPassword());
		pstmt.setString(4, user.getBirthday());
		
		flag = pstmt.executeUpdate();
		System.out.println("4. flag : \n : " + flag);
		
		pstmt.close();
		conn.close();
		
		return flag;
	}
	
	public User doSelectOne(User user) throws SQLException,ClassNotFoundException{
		
		User outVO = null;
		ResultSet rs = null;
		Connection conn = ConnectionConnecter.getConnection();
		System.out.println("1. conn : " + conn);
		
		StringBuilder sb = new StringBuilder();
		sb.append("select user_id,          \n ");
        sb.append("        name,            \n ");
        sb.append("        password,        \n ");
        sb.append("        birthday         \n ");
        sb.append("from member t1       	\n ");
        sb.append("where t1.user_id = ? 	\n ");
        
        System.out.println("2. SQL : \n : " + sb.toString());
		System.out.println("3. param : \n : " + user);
        
		PreparedStatement pstmt = conn.prepareStatement(sb.toString()); //conn.prepareStatement의 리턴 값은 PreparedStatement
		
		pstmt.setString(1, user.getUserId());
		
		rs = pstmt.executeQuery();
		//한 건 = if, 다 건 = while
		if(rs.next()) {
			
			outVO = new User();
			
			outVO.setUserId(rs.getString("user_id"));
			outVO.setName(rs.getString("name"));
			outVO.setPassword(rs.getString("password"));
			outVO.setBirthday(rs.getString("birthday"));
			
			System.out.println("4.outVO:" + outVO);
		}
		
		//자원반납
		rs.close();
		pstmt.close();
		conn.close();
		
		return outVO;
	}
}
