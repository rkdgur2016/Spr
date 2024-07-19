package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

public class UserService implements PLog{
	
	UserDao userDao;
	
	public UserService() {
		
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	/** 
	 * 전체 회원 등업
	 * @throws SQLException 
	 * */
	public void upgradeLevels() throws SQLException {
		//1. 전체 회원 조회
		
		//2. 등급 : BASIC, SILVER, GOLD 등업 조건
		//         BASIC : 최초가입
		//		   SILVER : 가입 후 50회 이상 로그인
		//		   GOLD : SILVER이면서 30회 이상 추천
		
		//3. 조건에 부합한 회원 등업
		
		//1. 
		List<User> userList = userDao.getAll();
		
		//2.
		for(User user : userList) {
			
			//level 변화 상태 값
			Boolean changed = null;
			
			//BASIC -> SILVER
			if(Level.BASIC == user.getLevel() && user.getLogin() >=50) {
				user.setLevel(Level.SILVER);
				changed = true;
			}else if(Level.SILVER == user.getLevel() && user.getRecommend() >= 30) {
				user.setLevel(Level.GOLD);
				changed = true;
			}else if(Level.GOLD == user.getLevel()) {
				changed = false;
			}else {
				changed = false;
			}
			
			//등업 : Level의 변경이 있는 경우만 doUpdate 호출
			if(true == changed) {
				userDao.updateOne(user);
			}
		}
	}
	
}
