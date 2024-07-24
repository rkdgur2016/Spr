package com.pcwk.ehr.user.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

@Service("userServiceImpl")
public class UserServiceImpl implements PLog, UserService{
	
	// 최소 로그인 횟수 : 50
	public static final int MIN_LOGINCOUNT_FOR_SILVER = 50;
	// 최소 추천 횟수 : 30
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;
	
	@Autowired
	@Qualifier("dummyMailService")
	private MailSender mailSender;
	
	@Autowired
	UserMapper userMapper; // UserMapper
	
	public UserServiceImpl() {
		
	} // 깡통임
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	/*
	 * 최초 가입시
	 * @param user
	 * @return user
	 * @throws SQLException
	 * */
	@Override
	public int doSave(User user) throws SQLException{
		int flag = 0;
		
		if(null == user.getLevel()) {
			log.debug("user.getLevel() : " + user.getLevel());
			user.setLevel(Level.BASIC);
		}
		flag = userMapper.doSave(user);
		log.debug("flag : " + flag);		
		
		return flag;
	}
	
	/*
	 * setter 주입
	 * @param userMapper
	 * */	
	public void setUserDao(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	// 등업 대상 추출
	public boolean canUpgradeLevel(User user){
		Level currentLevel = user.getLevel();
		
		switch (currentLevel) {
		case BASIC:
			return(user.getLogin() >= MIN_LOGINCOUNT_FOR_SILVER);
		case SILVER:
			return(user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
		case GOLD:
			return false;
		default: throw new IllegalArgumentException("Unknown Level");
		}
	}
	
	/*
	 * BASIC, SILVER 대상으로 등업
	 * @param user
	 * @throws SQLException
	 * */
	protected void upgradeLevel(User user) throws SQLException {
		
		user.upgradeLevel(); // 다음 Level		
		userMapper.doUpdate(user); // 등업
		
		sendUpgradeEmail(user);// 등업회원 이메일 전송
	}
	
	private void sendUpgradeEmail(User user){
		// org.springframework.mail
		
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			
			// 보내는 사람
			message.setFrom("bagsa1717@naver.com");
			
			// 받는 사람
			message.setTo(user.getEmail());
			
			// 제목 : 승급전 결과 안내문
			message.setSubject("오태시기 슬퍼숴 어쩌냐?");
			
			// 내용 : 홍길동님이 미쳐 날뛰고 있습니다. 골드 티어 승급 완료
			message.setText(user.getName() + "형은 나가 있어 뒤지기 싫으면, 나다 이 Tenguys" + user.getLevel() + " 레벨업 완료");
			
			mailSender.send(message);
		} catch (Exception e) {
			log.debug("┌─────────────────────────────────────────────────────────");
			log.debug("│ sendUpgradeEmail()" + e.getMessage());
			log.debug("└─────────────────────────────────────────────────────────");	
			e.printStackTrace();
		}
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ 전송끝났다 나가" + user.getName());
		log.debug("└─────────────────────────────────────────────────────────");	
	}
	
	/*
	 * 전체 회원 등업
	 * @throws SQLException
	 * */
	@Override
	public void upgradeLevelsTx() throws SQLException {
		// 1. 전체 회원 조회
		List<User> users = userMapper.getAll();
		
		// 2. 등급 : BASIC, SILVER, GOLD 등업 조건
		for (User user : users) {
			
			Boolean changed = null; // 레벨 변화 유무
			// 등업 대상 선정
			if(true == canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		} // for
	}

	@Override
	public int doDelete(User inVO) throws SQLException {
		log.debug("1. param : " + inVO);		
		int flag = this.userMapper.doDelete(inVO);
		log.debug("2. flag : " + flag);
		return flag;
	}

	@Override
	public List<User> doRetrieve(DTO search) throws SQLException {
		log.debug("1. param : " + search);	
		List<User> list = this.userMapper.doRetrieve(search);
		
		return list;
	}

	@Override
	public int doUpdate(User inVO) throws SQLException {
		log.debug("1. param : " + inVO);	
		int flag = this.userMapper.doUpdate(inVO);
		log.debug("2. flag : " + flag);
		
		return flag;
	}

	@Override
	public User doSelectOne(User inVO) throws SQLException{
		log.debug("1. param : " + inVO);	
		User outVO = this.userMapper.doSelectOne(inVO);
		log.debug("2. outVO : " + outVO);
		
		return outVO;
	}

	@Override
	public int idDuplicateCheck(User inVO) throws SQLException {
		log.debug("1. param : " + inVO);	
		int flag = this.userMapper.idDuplicateCheck(inVO);
		log.debug("2. flag : " + flag);
		
		return flag;
	}
	
}
