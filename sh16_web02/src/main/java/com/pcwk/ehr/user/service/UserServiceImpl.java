package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

@Service("userServiceImpl")
public class UserServiceImpl implements PLog, UserService {

	// 다른 곳에서 쓰기 위해서 public static final으로 작성
	// 최소 로그인 횟수 : 50
	public static final int MIN_LOGINCOUNT_FOR_SILVER = 50;
	// 최소 추천 횟수 : 30
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;

	@Autowired
	@Qualifier("dummyMailService")
	private MailSender mailSender;

	@Autowired
	UserMapper userMapper;

	public UserServiceImpl() {

	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 최초 가입시 Level이 Null이면 BASIC
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int doSave(User user) throws SQLException {
		int flag = 0;

		if (null == user.getLevel()) {
			log.debug("user.getLevel():" + user.getLevel());
			user.setLevel(Level.BASIC);
		}

		flag = userMapper.doSave(user);
		log.debug("flag:" + flag);

		return flag;
	}

	// 등업 대상 추출
	public boolean canUpgradeLevel(User user) throws SQLException {// 등업 : Level의 변경이 있는 경우만 doUpdate호출
		Level currentLevel = user.getLevel();

		switch (currentLevel) {
		case BASIC:
			return (user.getLogin() >= MIN_LOGINCOUNT_FOR_SILVER);
		case SILVER:
			return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
		case GOLD:
			return false;

		default:
			throw new IllegalArgumentException("Unknonw level");
		}

	}

	/**
	 * BASIC, SILVER대상으로 등업
	 * 
	 * @param user
	 * @throws SQLException
	 */
	// upgradeLevel
	protected void upgradeLevel(User user) throws SQLException {

		user.upgradeLevel(); // 다음 Level
		userMapper.doUpdate(user);// 등업

		sendUpgradeEmail(user);// 등업자 email 전송

	}

	private void sendUpgradeEmail(User user) {

		try {
			SimpleMailMessage message = new SimpleMailMessage();

			// 보내는 사람
			message.setFrom("ddswlstj@naver.com");

			// 받는 사람
			message.setTo(user.getEmail());

			// 제목
			message.setSubject("등업 안내");

			// 내용 : OOO님의 등급이 GOLD로 등업 되었습니다.
			message.setText(user.getName() + "님의 등급이" + user.getLevel() + "로 등업 되었습니다.");

			// 전송
			mailSender.send(message);

		} catch (Exception e) {
			log.debug("┌─────────────────────────────────────────────────────────┐");
			log.debug("│ sendUpgradeEmail()                                      │" + e.getMessage());
			log.debug("└─────────────────────────────────────────────────────────┘");
			e.printStackTrace();
		}

		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug(
				"│ mail  전송                                                                                                        │"
						+ user.getName());
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

	/**
	 * 전체 회원 등업
	 * 
	 * @throws SQLException
	 */
	@Override
	public void upgradeLevelsTx() throws SQLException {

		// 1.
		List<User> users = userMapper.getAll();

		// 2.
		for (User user : users) {

			// 등업 대상 선정
			if (true == canUpgradeLevel(user)) {
				upgradeLevel(user);
			}

		} // for

	}// upgradeLevels end

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
	public User doSelectOne(User inVO) throws SQLException, NullPointerException {
		log.debug("1. param : " + inVO);

		User outVO = this.userMapper.doSelectOne(inVO);
		log.debug("2. outVO : " + outVO);

		return outVO;
	}
	
	public int idDuplicateCheck(User inVO) throws SQLException{
		log.debug("1. param : " + inVO);
		
		int count = userMapper.idDuplicateCheck(inVO);
		log.debug("3. count : " + count);
		
		return count;
	}

}// class end