package com.pcwk.ehr.user.service;

import static org.junit.Assert.*;

import static com.pcwk.ehr.user.service.UserServiceImpl.MIN_LOGINCOUNT_FOR_SILVER;
import static com.pcwk.ehr.user.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

@RunWith(SpringRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //알파벳 순서로 테스트 메서드 실행
public class UserServiceImplTest implements PLog {
	
	@Autowired
	ApplicationContext context;

	@Autowired
	UserMapper userMapper;

	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	@Autowired
	TestUserService testUserService;
	
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
		
		//기존 데이터 삭제
		userMapper.deleteAll();
		
		// user 5명 생성
		// 1 BASIC
		// 2 BASIC -> SILVER(O)
		// 3 SILVER
		// 4 SILVER -> GOLD(O)
		// 5 GOLD
		users = Arrays.asList(
				new User("rogan01", "이상무01", "4321", "2002/12/31", Level.BASIC, MIN_LOGINCOUNT_FOR_SILVER -1, MIN_RECOMMEND_FOR_GOLD - 30, "ddswlstj@naver.com",
						"sysdate사용"),
				new User("rogan02", "이상무02", "4321", "2002/12/30", Level.BASIC, MIN_LOGINCOUNT_FOR_SILVER, MIN_RECOMMEND_FOR_GOLD - 28, "ddswlstj@naver.com",
						"sysdate사용"),
				new User("rogan03", "이상무03", "4321", "2002/12/29", Level.SILVER, MIN_LOGINCOUNT_FOR_SILVER + 1, MIN_RECOMMEND_FOR_GOLD -1, "ddswlstj@naver.com",
						"sysdate사용"),
				new User("rogan04", "이상무04", "4321", "2002/12/28", Level.SILVER, MIN_LOGINCOUNT_FOR_SILVER + 2, MIN_RECOMMEND_FOR_GOLD, "ddswlstj@naver.com",
						"sysdate사용"),
				new User("rogan05", "이상무05", "4321", "2002/12/27", Level.GOLD, MIN_LOGINCOUNT_FOR_SILVER + 5, MIN_RECOMMEND_FOR_GOLD + 2, "ddswlstj@naver.com",
						"sysdate사용"));
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}
	
	
	//@Ignore
	@Test
	//upgradeAllOrNothing
	public void upgradeAllOrNothing(){
		log.debug("┌──────────────────────────────────────┐");
		log.debug("│ upgradeAllOrNothing()                │");
		log.debug("└──────────────────────────────────────┘");
		
		
		//2. 5명 등록
		//3. 등업
		//4. checkLevel(users.get(1), false);
		
		try {
			
			assertEquals(0, userMapper.getCount());
			
			//2.
			for(User userVO : users) {
				userMapper.doSave(userVO);
			}
			assertEquals(5, userMapper.getCount());
			
			//3.
			testUserService.upgradeLevelsTx();
			
			checkLevel(users.get(1), false);
			
			//SQLException으로 하면 예외를 못잡기 때문에 Exception으로 해야한다.
		} catch (Exception e) {
			log.debug("┌──────────────────────────────────────┐");
			log.debug("│ RunTimeException()                   │" + e.getMessage());
			log.debug("└──────────────────────────────────────┘");
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void doSave() throws SQLException {
		log.debug("┌──────────────────────────────────────┐");
		log.debug("│ doSave()                             │");
		log.debug("└──────────────────────────────────────┘");
		
		
		//2. 등급있는 경우(GOLD), 등급이 Null
		User userWithLevel = users.get(4);//GODE
		
		User userWithOutLevel = users.get(0);
		userWithOutLevel.setLevel(null);//Level null
		
		//3. 2명 등록
		int flag = userService.doSave(userWithLevel);
		assertEquals(1, flag);
		
		flag = userService.doSave(userWithOutLevel);
		assertEquals(1, flag);
		
		//4. 데이터 조회
		User userWithLevelRead = userMapper.doSelectOne(userWithLevel);
		
		User userWithOutLevelRead = userMapper.doSelectOne(userWithOutLevel);
		
		assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel());
		assertEquals(userWithOutLevelRead.getLevel(), Level.BASIC);
		
	}
	
	@Ignore
	@Test
	public void upgradeLevels() throws SQLException {
		log.debug("┌──────────────────────────────────────┐");
		log.debug("│ upgradeLevels()                      │");
		log.debug("└──────────────────────────────────────┘");

		// user 5명 생성
		// 1 BASIC
		// 2 BASIC -> SILVER(O)
		// 3 SILVER
		// 4 SILVER -> GOLD(O)
		// 5 GOLD

//		for (User userVO : users) {
//			userMapper.doDelete(userVO);
//		}

		// B.users 모두 데이터 입력
		for (User userVO : users) {
			userMapper.doSave(userVO);
		}
		assertEquals(5, userMapper.getCount());

		// C.등업
		userService.upgradeLevelsTx();

		// D.등업 데이터 비교 rogan02(BASIC -> SILVER) rogan04(SILVER -> GOLD)
		checkLevel(users.get(0), false);
		checkLevel(users.get(1), true);
		checkLevel(users.get(2), false);
		checkLevel(users.get(3), true);
		checkLevel(users.get(4), false);
	}
	
	/**
	 * 
	 * @param userVO
	 * @param upgraded(true : 다음 레벨로 등업)
	 * @throws NullPointerException
	 * @throws SQLException
	 */
	public void checkLevel(User userVO, boolean upgraded) throws NullPointerException, SQLException {
		log.debug("┌──────────────────────────────────────┐");
		log.debug("│ checkLevel()                         │");
		log.debug("└──────────────────────────────────────┘");
		
		User userUpdate = userMapper.doSelectOne(userVO);
		
		if(true == upgraded) {
			//등업
			assertEquals(userUpdate.getLevel(), userVO.getLevel().nextLevel()); 
		}else {
			//등업 없음
			assertEquals(userUpdate.getLevel(), userVO.getLevel());
		}
		
	}
	
	@Ignore
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────┐");
		log.debug("│ beans()                      │");
		log.debug("└──────────────────────────────┘");
		log.debug("context:" + context);
		log.debug("userMapper:" + userMapper);
		log.debug("userService:" + userService);
		log.debug("testUserService:" + testUserService);
		
		assertNotNull(context);
		assertNotNull(userMapper);
		assertNotNull(userService);
		assertNotNull(testUserService);
		// assertNull(context);
	}

}
