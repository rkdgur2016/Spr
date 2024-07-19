package com.pcwk.ehr.user.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.dao.UserDaoJDBCTest;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class UserServiceTest {
	
	final static Logger log  = LogManager.getLogger(UserDaoJDBCTest.class);
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserService userService;
	
	
	List<User> userList;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ UserServiceTest :	@BeforeClass : setUpBeforeClass()    │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ UserServiceTest :	@Before : setUp()                    │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		//가변 리스트
		userList = Arrays.asList(
			new User("human01", "사람01", "1111", "2001-02-17",Level.BASIC, 49, 0, "rkdgur2016@gmail.com","sysdate사용"),
			new User("human02", "사람02", "2222", "2001-02-18",Level.BASIC, 50, 2, "rkdgur2016@gmail.com","sysdate사용"),
			new User("human03", "사람03", "3333", "2001-02-19",Level.SILVER, 51, 29, "rkdgur2016@gmail.com","sysdate사용"),
			new User("human04", "사람04", "4444", "2001-02-20",Level.SILVER, 52, 30, "rkdgur2016@gmail.com","sysdate사용"),
			new User("human05", "사람05", "5555", "2001-02-21",Level.GOLD, 55, 32, "rkdgur2016@gmail.com","sysdate사용")
		);
		
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ UserServiceTest :	@After : tearDown()                  │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}
	
	@Test
	public void upgradeLevels() throws SQLException{
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ UserServiceTest :	@Test : upgradeLevels()              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		//유저 5명 생성
		// 유저 1 : BASIC
		// 유저 2 : BASIC -> SILVER 등업 대상
		// 유저 3 : SILVER 
		// 유저 4 : SILVER -> GOLD 등업 대상
		// 유저 5 : GOLD 
		
		//A. 전체 데이터 삭제
		//B. userList 모두 데이터 입력
		//C. 등업
		//D. 등업 데이터 비교 (사람02.BASIC -> SILVER, 사람04.SILVER -> GOLD)
		
		//A.
		for(User user : userList) {
			userDao.deleteOne(user);
		}
		assertEquals(0, userDao.selectCnt());
		
		
		//B.
		for(User user : userList) {
			userDao.doSave(user);
		}
		assertEquals(5, userDao.selectCnt());
		
		//C
		userService.upgradeLevels();
		
		//D
		checkLevel(userList.get(0), Level.BASIC);
		checkLevel(userList.get(1), Level.SILVER);
		checkLevel(userList.get(2), Level.SILVER);
		checkLevel(userList.get(3), Level.GOLD);
		checkLevel(userList.get(4), Level.GOLD);
		
	}
	
	private void checkLevel(User userVO, Level expectedLevel) throws NullPointerException, SQLException {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ UserServiceTest :	@Test : checkLevel()                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		User userUpdate = userDao.doSelectOne(userVO);
		assertEquals(userUpdate.getLevel(), expectedLevel);
	}

	@Test
	public void beans() {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ UserServiceTest :	@Test : beans()                     │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		log.debug("context : " +context);
		log.debug("userDao : " + userDao);
		log.debug("userService : " + userService);
		assertNotNull(context);
		assertNotNull(userDao);
		assertNotNull(userService);
	}

}
