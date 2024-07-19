package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

public class UserServiceImpl implements PLog, UserService {
	
	
	
	public static final int MIN_LOGINCOUNT_FOR_SILVER = 50; //최소 로그인 횟수: 50 
	public static final int MIN_RECOMMEND_FOR_GOLD = 30; //최소 추천횟수 : 30
	
	
	private PlatformTransactionManager transactionManager;
	
	private DataSource dataSource;
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}


	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}



	UserDao userDao;

	public UserServiceImpl() {

	}
	
	/**
	 * 최초가입시 level이 null이면 basic
	 * @param user
	 * return
	 * throws SQLException
	 */
	
	
	@Override
	public int doSave(User user) throws SQLException{
		int flag = 0;
		
		if(null == user.getLevel()) {
			log.debug("user.getLevel()"+user.getLevel());
			user.setLevel(Level.BASIC);
		}
		
		flag = userDao.doSave(user);
		log.debug("flag:"+flag);
		return flag;
	}

	/**
	 * setter 주입
	 * @param userDao
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	
	///등업 대상 추출
	public boolean canUpgradeLevel(User user) { 
		Level currentLevel = user.getLevel();
		
		switch (currentLevel) {
		case BASIC: 
			return  (user.getLogin() >= MIN_LOGINCOUNT_FOR_SILVER); 
		case SILVER: 
			return  (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD); 	
		case GOLD: 
			return false;
		default: 
			throw new IllegalArgumentException("Unknonw level");
		}
	}
	
	
 
	
	/**
	 * BASIC, SILVER 대상으로 등업
	 * @param user
	 * @throws SQLException
	 */
	protected void upgradeLevel(User user) throws SQLException {
 
		user.upgradeLevel();   //다음Level
		userDao.doUpdate(user);
		
	}
	
	
	/**
	 * 전체 회원 등업
	 * @throws SQLException 
	 */
	@Override
	public void upgradeLevels() throws SQLException {
		//1. 전체 회원 조회
		//2. 등급: BASIC, SILVER, GOLD 등업 조건
		//		1. BASIC :  최초 가입
		//		2. SILVER : 가입 후 50회 이상 로그인
		//		3. GOLD   : SILVER이면서 30회 이상 추천		
		//3. 조건에 해당되는 회원 등업
		
		//트랜잭션 동기화 관리자로 동기화 작업 초기화
		//PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try { 
			
			//1
			List<User> users = userDao.getAll();
			 
			for(User user:users) {
				 
				//등업 대상 선정
				if(true == canUpgradeLevel(user)) {
					upgradeLevel(user);
				} 
				
			}
			transactionManager.commit(status);
		}catch (Exception e) { 
			log.debug("┌──────────────────────────────────────────┐");
			log.debug("│ Exception()    conn.rollback             │"+e.getMessage());
			log.debug("└──────────────────────────────────────────┘");
			transactionManager.rollback(status);
			
			throw e;
		}
		
	}

}













