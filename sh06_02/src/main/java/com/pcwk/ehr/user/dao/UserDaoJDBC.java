/**
 * Package Name : com.pcwk.ehr <br/>
 * Class Name: User.java <br/>
 * Description: 회원DAO <br/>
 * Modification information : <br/>
 * ------------------------------------------<br/>
 * 최초 생성일 : 2024.07.03
 * 			   2024.07.03 : 유저 Level 기능 추가
 *
 * ------------------------------------------<br/>
 * @author : user
 * @since  : 2023.09.07
 * @version: 0.5
 */
package com.pcwk.ehr.user.dao;

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

import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;


public class UserDaoJDBC implements UserDao {
	
	final Logger log = LogManager.getLogger(UserDaoJDBC.class);
	
	private RowMapper<User> userMapper = new RowMapper<User>() {
		
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User outUser = new User();
			outUser.setUserId(rs.getString("user_id"));
			outUser.setName(rs.getString("name"));
			outUser.setPassword(rs.getString("password"));
			outUser.setBirthday(rs.getString("birthday"));
			//파라미터로 level값을 받아야 하는데 DB에서는 int 값을 가져옴 => Level 타입으로 형변환
			outUser.setLevel(Level.valueOf(rs.getInt("u_level")));
			outUser.setLogin(rs.getInt("login"));
			outUser.setRecommend(rs.getInt("recommend"));
			outUser.setEmail(rs.getString("email"));
			outUser.setRegDt(rs.getString("reg_dt"));
			return outUser;
		}
	};
	
	private JdbcTemplate jdbcTemplate;
	
	private DataSource dataSource;
	
	public UserDaoJDBC() {
		//기본 생성자는 필수
	}
	
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		
		//jdbcTemplate 객체 생성
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int updateOne(User user)throws SQLException{
		log.debug("──────────────────");
		log.debug("UserDao : updateOne()");
		log.debug("──────────────────");
		int flag = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE member            \n");
		sb.append("SET                      \n");
		sb.append("     name =      ?,    	\n");
		sb.append("     password =  ?,    	\n");
		sb.append("     birthday =  ?,    	\n");
		sb.append("     u_level =   ?,    	\n");
		sb.append("     login =     ?,    	\n");
		sb.append("     recommend = ?,    	\n");
		sb.append("     email =     ?,    	\n");
		sb.append("     reg_dt =    SYSDATE \n");
		sb.append("WHERE                    \n");
		sb.append("        user_id = ?      \n");
		
		log.debug("1. SQL : " + sb.toString());
		
		Object[] args= {
				 user.getName(),
				 user.getPassword(),
				 user.getBirthday(),
				 //int 값으로 받아와야함 intValue()
				 user.getLevel().intValue(),
				 user.getLogin(),
				 user.getRecommend(),
				 user.getEmail(),
				 user.getUserId()
		};
		
		for(Object obj : args) {
			log.debug("2. param: " + obj.toString());
		}
		
		flag = jdbcTemplate.update(sb.toString(), args);
		log.debug("3. flag : " + flag);
		
		return flag; 
	}
	
	@Override
	public int deleteOne(User user)throws SQLException {
		log.debug("──────────────────");
		log.debug("deleteOne()");
		log.debug("──────────────────");
		
		int flag = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM member \n"); 
		sb.append(" WHERE user_id = ? \n");
		
		log.debug("1. SQL : " + sb.toString());
		
		// SQL에 ? 부분에 들어갈 argument
		Object[] args = {user.getUserId()};

		for(Object obj : args) {
			log.debug("2. args : " + obj);
		}

		flag = jdbcTemplate.update(sb.toString(),args);
		log.debug("3. flag : " + flag);

		return flag;
	}
	
	@Override
	public List<User> getAll(){

		List<User> list = new ArrayList<User>();
		StringBuilder sb = new StringBuilder();
	    sb.append("     SELECT                               \n ");  
	    sb.append("     user_id,                             \n ");  
	    sb.append("     name,                                \n ");  
	    sb.append("     password,                            \n ");  
	    sb.append("     birthday,                            \n ");  
	    sb.append("     u_level,                             \n ");  
	    sb.append("     login,                               \n ");  
	    sb.append("     recommend,                           \n ");  
	    sb.append("     email,                               \n ");  
	    sb.append("     TO_CHAR(reg_dt, 'YYYY-MM-DD') reg_dt \n ");  
	    sb.append(" FROM                                     \n ");  
	    sb.append("     member                               \n ");  
	    sb.append(" ORDER BY user_id              		 	 \n ");  
		
		log.debug("1. SQL : " + sb.toString());
		
		list = jdbcTemplate.query(sb.toString(), userMapper);
		
		for(User user : list) {
			log.debug(user);
		}
		
		return list;
	}
	
	//테스트용 등록된 데이터 건수 조회
	@Override
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
	@Override
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
	
	@Override
	public int doSave(User user) throws SQLException{
		log.debug("──────────────────");
		log.debug("doSave()");
		log.debug("──────────────────");
		int flag = 0;
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO member (\n");
		sb.append("	    user_id,       \n");
		sb.append("	    name,          \n");
		sb.append("	    password,      \n");
		sb.append("	    birthday,      \n");
		sb.append("	    u_level,       \n");
		sb.append("	    login,         \n");
		sb.append("	    recommend,     \n");
		sb.append("	    email,         \n");
		sb.append("	    reg_dt         \n");
		sb.append("	) VALUES (         \n");
		sb.append("	    ?,             \n");
		sb.append("	    ?,             \n");
		sb.append("	    ?,             \n");
		sb.append("	    ?,             \n");
		sb.append("	    ?,             \n");
		sb.append("	    ?,             \n");
		sb.append("	    ?,             \n");
		sb.append("	    ?,             \n");
		sb.append("	    sysdate        \n");
		sb.append("	)                  \n");
		
		log.debug("1. SQL : \n : " + sb.toString());
		
		Object[] args = {user.getUserId(),
						 user.getName(),
						 user.getPassword(),
						 user.getBirthday(),
						 user.getLevel().intValue(),
						 user.getLogin(),
						 user.getRecommend(),
						 user.getEmail()
						 };
		
		for(Object obj : args) {
			log.debug("2. args : " + obj);
		}
		
		flag = jdbcTemplate.update(sb.toString(),args);
		log.debug("3. flag : " + flag);
		
		return flag;
	}
	
	@Override
	public User doSelectOne(User user) throws SQLException, NullPointerException {
		log.debug("──────────────────");
		log.debug("doSelectOne()");
		log.debug("──────────────────");
		
		User outVO = null;
		ResultSet rs = null;
		Connection conn = dataSource.getConnection();
		log.debug("1. conn : " + conn);
		
		StringBuilder sb = new StringBuilder();
	    sb.append("     SELECT                               \n ");  
	    sb.append("     user_id,                             \n ");  
	    sb.append("     name,                                \n ");  
	    sb.append("     password,                            \n ");  
	    sb.append("     birthday,                            \n ");  
	    sb.append("     u_level,                             \n ");  
	    sb.append("     login,                               \n ");  
	    sb.append("     recommend,                           \n ");  
	    sb.append("     email,                               \n ");  
	    sb.append("     TO_CHAR(reg_dt, 'YYYY-MM-DD') reg_dt \n ");  
	    sb.append(" FROM                                     \n ");  
	    sb.append("     member                               \n ");  
	    sb.append(" WHERE user_id = ?               		 \n ");  
                                                      
        log.debug("2. SQL : \n : " + sb.toString());
		log.debug("3. param : \n : " + user);
        
		//where에 들어갈 user_id 값 세팅
		Object[] args = {user.getUserId()};
		
		outVO = jdbcTemplate.queryForObject(sb.toString(), userMapper, args);
		
		log.debug("4. outVO : " + outVO);
		
		return outVO;
	}
}