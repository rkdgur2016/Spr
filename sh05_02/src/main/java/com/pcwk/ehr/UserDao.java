package com.pcwk.ehr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class UserDao {
	
	final Logger log = LogManager.getLogger(UserDao.class);
	
	private RowMapper<User> userMapper = new RowMapper<User>() {
		
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User outUser = new User();
			outUser.setUserId(rs.getNString("user_id"));
			outUser.setName(rs.getString("name"));
			outUser.setPassword(rs.getString("password"));
			outUser.setBirthday(rs.getString("birthday"));
			
			return outUser;
		}
	};
	
	private JdbcTemplate jdbcTemplate;
	
	private DataSource dataSource;
	
	public UserDao() {
		//기본 생성자는 필수
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		
		//jdbcTemplate 객체 생성
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<User> getAll(){

		List<User> list = new ArrayList<User>();
		StringBuilder sb = new StringBuilder();
		sb.append("	SELECT			\n");
		sb.append("		user_id,    \n");
		sb.append("		name,       \n");
		sb.append("		password,   \n");
		sb.append("		birthday    \n");
		sb.append("FROM             \n");
		sb.append("		member		\n");
		sb.append("ORDER BY user_id \n");
		
		log.debug("1. SQL : " + sb.toString());
		
		list = jdbcTemplate.query(sb.toString(), userMapper);
		
		for(User user : list) {
			log.debug(user);
		}
		
		return list;
	}
	
	//테스트용 등록된 데이터 건수 조회
	public int selectCnt() throws SQLException {
		log.debug("──────────────────");
		log.debug("selectCnt()");
		log.debug("──────────────────");
		int count = 0;
		
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT count(*) cnt FROM member \n");
		log.debug("2. SQL : \n" + sb.toString());
	
		//queryForObject(SQL, 리턴 클래스 타입);
		count = jdbcTemplate.queryForObject(sb.toString(),Integer.class);
			
		log.debug("3. count : " + count);
		
		return count;
	}
	
	//테스트용 전체 데이터 삭제
	public int deleteAll() throws SQLException {
		log.debug("──────────────────");
		log.debug("deleteAll()");
		log.debug("──────────────────");
		int flag = 0;
		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM member \n");
		log.debug("1. SQL : " + sb.toString());
		
		//return : int, update(...) : (가변인자)
		flag = jdbcTemplate.update(sb.toString());
		log.debug("2. flag : " + flag);
				
		return flag;
	}
	
	public int jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		log.debug("──────────────────");
		log.debug("jdbcContextWithStatementStrategy()");
		log.debug("──────────────────");
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
		log.debug("──────────────────");
		log.debug("doSave()");
		log.debug("──────────────────");
		int flag = 0;
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO MEMBER VALUES(?, ?, ?, ?) \n");
		log.debug("1. SQL : \n : " + sb.toString());
		
		Object[] args = {user.getUserId(), user.getName(), user.getPassword(), user.getBirthday()};
		
		for(Object obj : args) {
			log.debug("2. args : " + obj);
		}
		
		flag = jdbcTemplate.update(sb.toString(),args);
		log.debug("3. flag : " + flag);
		
		return flag;
	}
	
	public User doSelectOne(User user) throws SQLException, NullPointerException {
		log.debug("──────────────────");
		log.debug("doSelectOne()");
		log.debug("──────────────────");
		
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
        
		//where에 들어갈 user_id 값 세팅
		Object[] args = {user.getUserId()};
		
		outVO = jdbcTemplate.queryForObject(sb.toString(), userMapper, args);
		
		log.debug("4. outVO : " + outVO);
		
		return outVO;
	}
}