package com.pcwk.ehr;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

public interface UserDao {

	void setDataSource(DataSource dataSource);

	int updateOne(User user) throws SQLException;

	int deleteOne(User user) throws SQLException;
	
	/** 
	 * 전체 회원 조회
	 * @return List<User>
	 * */
	List<User> getAll();

	/** 
	 * 테스트용 전체 회원 수
	 * @return int
	 * @throws SQLException
	 * */
	int selectCnt() throws SQLException;

	//테스트용 전체 데이터 삭제
	/** 
	 * 테스트용 전체 데이터 삭제
	 * @return
	 * @throws SQLException
	 * */
	int deleteAll() throws SQLException;

	/** 
	 * 회원 정보 단건 등록
	 * @param user
	 * @return 1(성공/0(실패)
	 * @throws SQLException
	 * */
	int doSave(User user) throws SQLException;

	/** 
	 * 회원 정보 단건 조회
	 * 
	 * */
	User doSelectOne(User user) throws SQLException, NullPointerException;

}