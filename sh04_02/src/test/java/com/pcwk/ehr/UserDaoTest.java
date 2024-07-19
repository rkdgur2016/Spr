package com.pcwk.ehr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner; //Spring TestContext Framework » 5.3.37

@RunWith(SpringRunner.class) //스프링 컨텍스트 프레임워크의 JUnit 확장 기능 지정
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"}) //xml 파일의 위치를 복수형태로 줄 수 있다.
public class UserDaoTest {

	final static Logger log  = LogManager.getLogger(UserDaoTest.class);
	
	@Autowired
	UserDao dao;
	
	@Autowired
	ApplicationContext context;
	
	User userVO01;
	User userVO02;
	User userVO03;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.debug("===================");
		log.debug("setUpBeforeClass() : ");
		log.debug("===================");
	}

	@Before
	public void setUp() throws Exception {

		//테스트 메서드 수행시 마다 1회 수행
		log.debug("context : " + context);
		//dao = context.getBean("userDao", UserDao.class);
		log.debug("dao : " + dao);
		
		userVO01 = new User("human01", "사람01", "1111", "2001-02-17");
		userVO02 = new User("human02", "사람02", "1111", "2001-02-18");
		userVO03 = new User("human03", "사람03", "1111", "2001-02-19");
		
	}
	
	
	@Test(expected = NullPointerException.class)
	public void getFailure() throws SQLException {
		log.debug("===================");
		log.debug("Faliure()");
		log.debug("===================");
		
		//1. 전체 데이터 삭제
		//2. 한 건 등록
		//3. 한 건 (없는 user_id) 조회
		
		//1. 
		dao.deleteAll();
		
		//2. 한 건 등록
		int flag = dao.doSave(userVO01);
		assertEquals(1, flag);
		
		//3.
		userVO01.setUserId("user_id없음");
		log.debug("───────────────────────");
		log.debug("failure : user_id 없음 ");
		log.debug("userVO01 : " + userVO01);
		log.debug("───────────────────────");
		
		User outVO = dao.doSelectOne(userVO01);
	}
	
	
	@Test(timeout = 3000) //메소드 수행시간 1/1000
	public void addAndGet() throws SQLException{
		log.debug("===================");
		log.debug("addAndGet()");
		log.debug("===================");
		//0. 전체 삭제
		//1. 등록
		//2. 한 건 조회
		
		log.debug("───────────────────────");
		log.debug("1번 ");
		log.debug("───────────────────────");
		int cnt = dao.deleteAll();
		log.debug("cnt : " + cnt);
		
		int flag = dao.doSave(userVO01);
		log.debug("1.1 등록 flag : " + flag);
		assertEquals(1, flag);
		
		int count = dao.selectCnt();
		log.debug("1.2 조회 : " + count);
		assertEquals(1, count);
		
		//asertEquals(나와야 되는 값, 실제 값);
		
		User outVO = dao.doSelectOne(userVO01);
		assertNotNull(outVO);
		isSameUser(userVO01, outVO);
		
		log.debug("userVO : " + userVO01);
		log.debug("outVO : " + outVO);
		
		//2번째 User
		log.debug("───────────────────────");
		log.debug("2번 ");
		log.debug("───────────────────────");
		flag = dao.doSave(userVO02);
		log.debug("2.1 등록 flag : " + flag);
		assertEquals(1, flag);
		
		count = dao.selectCnt();
		log.debug("2.2 조회 : " + count);
		assertEquals(2, count);
		
		log.debug("userVO : " + userVO02);
		
		User outVO02 = dao.doSelectOne(userVO02);
		assertNotNull(outVO02);
		isSameUser(userVO02, outVO02);
		
		//3번째 User
		log.debug("───────────────────────");
		log.debug("3번 ");
		log.debug("───────────────────────");
		flag = dao.doSave(userVO03);
		log.debug("3.1 등록 flag : " + flag);
		assertEquals(1, flag);
		
		log.debug("userVO : " + userVO03);
		log.debug("outVO : " + outVO);
		
		count = dao.selectCnt();
		log.debug("3.2 조회 : " + count);
		assertEquals(3, count);
		//오브젝트 비교도 가능하다.
		//assertEquals(userVO, outVO);

		User outVO03 = dao.doSelectOne(userVO03);
		assertNotNull(outVO03);
		isSameUser(userVO03, outVO03);
	}
	
	public void isSameUser(User userVO, User outVO) {
		assertEquals(userVO.getUserId(), outVO.getUserId());
		assertEquals(userVO.getName(), outVO.getName());
		assertEquals(userVO.getPassword(), outVO.getPassword());
		assertEquals(userVO.getBirthday(), outVO.getBirthday());
	}
	
	@Test
	public void beans() {
		//null이 아니면 true
		assertNotNull(context);
		assertNotNull(dao);
		log.debug("beans()");
	}
}
