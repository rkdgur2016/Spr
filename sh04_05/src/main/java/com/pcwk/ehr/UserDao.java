package com.pcwk.ehr;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class UserDao {
	
	final Logger log = LogManager.getLogger(UserDao.class);
	
	private JdbcContext jdbcContext;
	
	private DataSource dataSource;
	
	public UserDao() {
		//기본 생성자는 만들어둔다.
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}

	//테스트용 등록된 데이터 건수 조회
	public int selectCnt() throws SQLException {
		int count = 0;
		
		Connection conn = dataSource.getConnection();
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT count(*) cnt FROM member \n");
		log.debug("2. sql : \n" + sb.toString());
		
		log.debug("3. conn : " + conn);
		
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		log.debug("4. pstmt : " + pstmt);
		
		ResultSet rs = pstmt.executeQuery();
		log.debug("4. rs :  : " + rs);
		
		if(rs.next()) {
			count = rs.getInt("cnt");
		}
		log.debug("5. count : " + count);
		
		pstmt.close();
		conn.close();
		rs.close();
		
		return count;
	}
	
	//테스트용 전체 데이터 삭제
	public int deleteAll() throws SQLException {
		int flag = 0;
		
		flag = jdbcContext.workWithSatetmentStrategy(new StatementStrategy() {

				@Override
				public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
					
					PreparedStatement pstmt = null;
					
					StringBuilder sb = new StringBuilder();
					sb.append(" DELETE FROM member \n");
					log.debug("2. sql : \n" + sb.toString());
					
					pstmt = conn.prepareStatement(sb.toString());
					log.debug("3. PreparedStatement : " + pstmt);
					
					return pstmt;
				}
				
		});
		
		return flag;
	}
	
	public int jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		int flag = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn= dataSource.getConnection();
			log.debug("3. Connection : " + conn);
			
			pstmt = stmt.makePreparedStatement(conn);
			
			flag = pstmt.executeUpdate();
			log.debug("4. flag : " + flag);
			
		}catch(SQLException e) {
			log.debug("SQLException : " + e.getMessage());
			throw e;
		}finally {
			if(null != pstmt) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					
				}
				
			}
			if(null != conn) {
				try {
					conn.close();
					
				}catch(SQLException e) {

				}
			}
		}
		return flag;
	}
	
	public int doSave(User user) throws SQLException{
		int flag = 0;
		
		flag = jdbcContext.workWithSatetmentStrategy(new StatementStrategy() {

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
				
			}); //conn.prepareStatement의 리턴 값은 PreparedStatement
		
		return flag;
	}
	
	public User doSelectOne(User user) throws SQLException, NullPointerException {
		
		User outVO = null;
		ResultSet rs = null;
		Connection conn = dataSource.getConnection();
		log.debug("1. conn : " + conn);
		
		StringBuilder sb = new StringBuilder();
		sb.append("select user_id,          \n ");
        sb.append("        name,            \n ");
        sb.append("        password,        \n ");
        sb.append("        birthday         \n ");
        sb.append("from member t1       	\n ");
        sb.append("where t1.user_id = ? 	\n ");
        
        log.debug("2. SQL : \n : " + sb.toString());
		log.debug("3. param : \n : " + user);
        
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
			
			log.debug("4.outVO:" + outVO);
		}
		
		//nullPointerException
		if(null == outVO) {
			throw new NullPointerException(user.getUserId()+"를 확인 하세요.");
		}		
		//자원반납
		rs.close();
		pstmt.close();
		conn.close();
		
		return outVO;
	}

}