package com.pcwk.ehr.service;

import static com.pcwk.ehr.user.service.UserServiceImpl.MIN_LOGINCOUNT_FOR_SILVER;
import static com.pcwk.ehr.user.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

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
import org.springframework.transaction.PlatformTransactionManager;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;
import com.pcwk.ehr.user.service.TestUserService;
import com.pcwk.ehr.user.service.UserService;
import com.pcwk.ehr.user.service.UserServiceImpl;

@RunWith(SpringRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 알파벳 순서대로 메서드 실행
public class UserServiceTest implements PLog{

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
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ setUpBeforeClass()                                      ");
		log.debug("└─────────────────────────────────────────────────────────");	
	}

	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ setUp()	                                             ");
		log.debug("└─────────────────────────────────────────────────────────");	
		
		// user 5명 생성
		// 1. BASIC
		// 2. BASIC -> SILVER(O)
		// 3. SILVER
		// 4. SILVER -> GOLD(O)
		// 5. GOLD
		

		// 1A. 기존 데이터 삭제
		this.userMapper.deleteAll();
		assertEquals(userMapper.getCount(), 0);
		
		users = Arrays.asList(
			new User("james01", "이상무01", "4321", "2002/12/31", Level.BASIC, MIN_LOGINCOUNT_FOR_SILVER - 1, MIN_RECOMMEND_FOR_GOLD - 30, "bagsa1717@naver.com", "SYSDATE 사용"),
			new User("james02", "이상무02", "4321", "2002/12/30", Level.BASIC, MIN_LOGINCOUNT_FOR_SILVER, MIN_RECOMMEND_FOR_GOLD - 28, "bagsa1717@naver.com", "SYSDATE 사용"),
			new User("james03", "이상무03", "4321", "2002/12/29", Level.SILVER, MIN_LOGINCOUNT_FOR_SILVER + 1, MIN_RECOMMEND_FOR_GOLD - 1, "bagsa1717@naver.com", "SYSDATE 사용"),
			new User("james04", "이상무04", "4321", "2002/12/28", Level.SILVER, MIN_LOGINCOUNT_FOR_SILVER + 2, MIN_RECOMMEND_FOR_GOLD, "bagsa1717@naver.com", "SYSDATE 사용"),
			new User("james05", "이상무05", "4321", "2002/12/27", Level.GOLD, MIN_LOGINCOUNT_FOR_SILVER + 5, MIN_RECOMMEND_FOR_GOLD + 2, "bagsa1717@naver.com", "SYSDATE 사용")
		);
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ tearDown()	                                             ");
		log.debug("└─────────────────────────────────────────────────────────");	
	}
	
	// upgradeAllorNothing
	@Test
	public void upgradeAllorNothing(){		
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ upgradeAllorNothing()");
		log.debug("└─────────────────────────────────────────────────────────");
		
		// 1. 기존데이터 모두 삭제 set에 존재
		try {
			
		// 2. 5명 등록
			for (User userVO : users) {
				userMapper.doSave(userVO);
			}
			assertEquals(5, userMapper.getCount());
			
			testUserService.upgradeLevelsTx();
			
			checkLevel(users.get(1), false);
			
		} catch (Exception e) {
			log.debug("┌─────────────────────────────────────────────────────────");
			log.debug("│ Exception()" + e.getMessage());
			log.debug("└─────────────────────────────────────────────────────────");
			
			e.printStackTrace();
		}
		
		
		// 3. 등업
		// 4. checkLevel(users.get(1), false);
	}
	
	@Test
	public void doSave() throws SQLException{
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ doSave()	                                             ");
		log.debug("└─────────────────────────────────────────────────────────");		
		
		// 2. 등급있는 경우(GOLD), 등급이 Null
		User userWithLevel = users.get(4); // GOLD
		User userWithOutLevel = users.get(0);
		userWithOutLevel.setLevel(null); // Level null
		
		// 3. 2명 등록
		int flag = userService.doSave(userWithLevel);
		assertEquals(1, flag);
		
		// 4. 데이터 조회
		flag = userService.doSave(userWithOutLevel);
		assertEquals(1, flag);

		// 5. 비교
		User userWithLevelRead = userMapper.doSelectOne(userWithLevel);
		
		User userWithoutLevelRead = userMapper.doSelectOne(userWithOutLevel);
		
		assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel());
		assertEquals(userWithoutLevelRead.getLevel(), Level.BASIC);
	}

	@Test
	public void upgradeLevels() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ upgradeLevels()	                                     ");
		log.debug("└─────────────────────────────────────────────────────────");
			
		// A. 전체 데이터 삭제 set에 존재
		// B. users 모두 데이터 입력
		// C. 등업
		// D. 등업 데이터 비교 james02(BASIC -> SILVER) james04(SILVER -> GOLD)
		
		// B.
		for (User userVO : users) {
			userMapper.doSave(userVO);
		}
		assertEquals(5, userMapper.getCount());
		
		// C.
		userService.upgradeLevelsTx();
		
		// D. 등업 대상이면 TRUE 아니라면 FALSE를 도출한다.
		checkLevel(users.get(0), false);
		checkLevel(users.get(1), true);
		checkLevel(users.get(2), false);
		checkLevel(users.get(3), true);
		checkLevel(users.get(4), false);
	}
	
	/*
	 * @param userVO
	 * @param upgraed(true : 다음 레벨로 등업)
	 * @throws NullPointerException
	 * @throws SQLException
	 * */
	
	private void checkLevel(User userVO, boolean upgraded) throws NullPointerException, SQLException {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ checkLevel()	                                     	 ");
		log.debug("└─────────────────────────────────────────────────────────");
		
		User userUpdate = userMapper.doSelectOne(userVO);
		
		if (true == upgraded) {
			// 등업
			assertEquals(userUpdate.getLevel(), userVO.getLevel().nextLevel());
		}else {
			// 등업 없음
			assertEquals(userUpdate.getLevel(), userVO.getLevel());	
		}
	}
	
	@Ignore
	@Test
	public void beans() {
		log.debug("┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰┰");
		log.debug("━━━━━━━━━━━━━━━beans━━━━━━━━━━━━━━━");
		log.debug("context : " + context);
		log.debug("userMapper : " + userMapper);
		log.debug("userService : " + userService);
		log.debug("testUserService : " + testUserService);
		log.debug("┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻┻");
		assertNotNull(context);
		assertNotNull(userMapper);
		assertNotNull(userService);
		assertNotNull(testUserService);
	}

}
