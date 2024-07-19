package com.pcwk.ehr.user.service;

import static org.junit.Assert.*;
import static com.pcwk.ehr.user.service.UserServiceImpl.MIN_LOGINCOUNT_FOR_SILVER; 
import static com.pcwk.ehr.user.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

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
import org.springframework.transaction.PlatformTransactionManager;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

@RunWith(SpringRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class UserServiceImplTest implements PLog {

	@Autowired
	ApplicationContext context;

	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	DataSource dataSource;

	
	List<User> users;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUpBeforeClass()                                      │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		// user 5명 생성
		// 1 BASIC
		// 2 BASIC -> SILVER(O)
		// 3.SILVER
		// 4.SILVER -> GOLD(O)
		// 5.GOLD
		users = Arrays.asList(
				new User("human01", "사람01", "1111", "2001-02-17",Level.BASIC, 49, 0, "rkdgur2016@gmail.com","sysdate사용"),
				new User("human02", "사람02", "2222", "2001-02-18",Level.BASIC, 50, 2, "rkdgur2016@gmail.com","sysdate사용"),
				new User("human03", "사람03", "3333", "2001-02-19",Level.SILVER, 51, 29, "rkdgur2016@gmail.com","sysdate사용"),
				new User("human04", "사람04", "4444", "2001-02-20",Level.SILVER, 52, 30, "rkdgur2016@gmail.com","sysdate사용"),
				new User("human05", "사람05", "5555", "2001-02-21",Level.GOLD, 55, 32, "rkdgur2016@gmail.com","sysdate사용"));
	}
	
	@Ignore
	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}
	
	
	@Ignore
	//upgradeAllOrNothing
	@Test
	public void upgradeAllOrNothing() throws SQLException{
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ upgradeAllOrNothing()                                   │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		
		//1. 기본 데이터 모두 삭제
		//2. 5명 등록
		//3. 등업
		//4. checkLevel(user.get(1), false);
		 
		
		UserServiceImpl testUserService = new TestUserService(users.get(3).getUserId());
		testUserService.setUserDao(userDao); //수동으로 DI
		//수동으로 dataSource DI
		testUserService.setDataSource(dataSource);
		//수동으로 dataSource DI
		testUserService.setTransactionManager(transactionManager);
		
		//1
		try {
			userDao.deleteAll();
			assertEquals(0,userDao.getCount());
			
			for( User userVO :users) {
				userDao.doSave(userVO);
				
			}
			assertEquals(5,userDao.getCount() );
			 
			testUserService.upgradeLevels();
			checkLevel(users.get(1), false);
			//fail("TestUserServiceException expected");
			 
			
			
		} catch (Exception e) {  
			log.debug("┌──────────────────────────────────────────┐");
			log.debug("│ SQLException()                           │");
			log.debug("└──────────────────────────────────────────┘");
			e.printStackTrace();
			
		}
	}
	
	@Test
	public void doSave() throws SQLException{
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ doSave()                                                │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		//1.기존데이터 삭제
		//2.등급이 있는 경우(GOLD), 등급이  null
		//3.2명 등록
		//4.데이터 조회
		//5.비교
		
		//1
		userDao.deleteAll();
		
		//2
		User userWithLevel = users.get(4); //gold
		log.debug("userWithLevel : " + userWithLevel);
		User userWithOutLevel = users.get(0);
		log.debug("userWithOutLevel : " + userWithOutLevel);
		userWithOutLevel.setLevel(null);//level null
		
		//3
		int flag = userService.doSave(userWithLevel);
		assertEquals(1, flag);
		
		flag = userService.doSave(userWithOutLevel);
		assertEquals(1, flag);
		
		//4
		User userWithLevelRead = userDao.doSelectOne(userWithLevel);
		User userWithoutLevelRead = userDao.doSelectOne(userWithOutLevel);
		
		assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel());
		assertEquals(userWithoutLevelRead.getLevel(), Level.BASIC);
		
	}
	
	@Ignore
	@Test
	public void upgradeLevels() throws SQLException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ upgradeLevels()                          │");
		log.debug("└──────────────────────────────────────────┘");

		userDao.deleteAll();
		assertEquals(0,userDao.getCount() );
		
		// B.
		for (User userVO : users) {
			userDao.doSave(userVO);
		}
		assertEquals(5,userDao.getCount());
		
		// C
		userService.upgradeLevels();
		
		// D
		checkLevel(users.get(0), false);
		checkLevel(users.get(1), true);
		checkLevel(users.get(2), false);
		checkLevel(users.get(3), true);
		checkLevel(users.get(4), false);

	}

	/** 
	 * @param userVO
	 * @param upgraded(true : 다음 레벨로 등업)
	 * @throws NullPointerException
	 * @throws SQLException
	 */
	private void checkLevel(User userVO, boolean upgraded) throws NullPointerException, SQLException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ checkLevel()                             │");
		log.debug("└──────────────────────────────────────────┘");		
		
		User userUpdate = userDao.doSelectOne(userVO);
		if(true == upgraded) {
			assertEquals(userUpdate.getLevel(),userVO.getLevel().nextLevel());
		}else {
			assertEquals(userUpdate.getLevel(),userVO.getLevel());
		} 
	}
	
	@Ignore
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                                  │");
		log.debug("└──────────────────────────────────────────┘");
		log.debug("context:" + context);
		log.debug("userDao:" + userDao);
		log.debug("userService:" + userService);
		log.debug("transactionManager : " + transactionManager);
		assertNotNull(context);
		assertNotNull(userDao);
		assertNotNull(userService);
		assertNotNull(dataSource);
		assertNotNull(transactionManager);
		// assertNull(context);
	}

}