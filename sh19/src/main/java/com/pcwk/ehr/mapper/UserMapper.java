package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.user.domain.User;

@Mapper
public interface UserMapper extends WorkDiv<User> {
	
	/**
	 * 아이디 중복 체크
	 * @param invo
	 * @return 1(중복) , 0(중복x)
	 * @throws SQLException
	 */
	int idDuplicateCheck(User inVO) throws SQLException;
	
	/**
	 * 테스트용 101건 데이터 입력
	 * @return
	 * @throws SQLException
	 */
	int multipleSave() throws SQLException;

	/*
	 * 전체 회원 조회
	 * @return List<User>
	 * */
	List<User> getAll();

	// 테스트용 등록된 데이터 건수 조회
	/*
	 * 전체회원 수
	 * @return int
	 * @throws SQLException
	 * */
	int getCount() throws SQLException;

	// 테스트용 전체 데이터 삭제
	/*
	 * 테스트용 전체 데이터 삭제
	 * @return
	 * @throws SQLException
	 * */
	int deleteAll() throws SQLException; // 테스트용 전체 데이터 삭제 끝
	
}
