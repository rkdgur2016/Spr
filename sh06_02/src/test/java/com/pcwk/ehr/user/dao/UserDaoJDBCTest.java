package com.pcwk.ehr.user.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

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

import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

@RunWith(SpringRunner.class) //스프링 컨텍스트 프레임워크의 JUnit 확장 기능 지정
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"}) //xml 파일의 위치를 복수형태로 줄 수 있다.
public class UserDaoJDBCTest {

	final static Logger log  = LogManager.getLogger(UserDaoJDBCTest.class);
	
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
		log.debug("@BeforeClass");
		log.debug("===================");
	}

	@Before
	public void setUp() throws Exception {
		log.debug("===================");
		log.debug("@Before() : ");
		log.debug("===================");
		//테스트 메서드 수행시 마다 1회 수행
		log.debug("context : " + context);
		//dao = context.getBean("userDao", UserDao.class);
		log.debug("dao : " + dao);
		
		userVO01 = new User("human01", "사람01", "1111", "2001-02-17",Level.BASIC, 1, 0, "rkdgur2016@gmail.com","sysdate사용");
		userVO02 = new User("human02", "사람02", "1111", "2001-02-18",Level.SILVER,50,2, "limrkdgur2016@naver.com", "sysdate사용");
		userVO03 = new User("human03", "사람03", "1111", "2001-02-19",Level.GOLD, 100,31, "rkdgur2016@gmail.com", "sysdate사용");
		
	}
	
	@Ignore
	@Test
	public void getAll() throws SQLException{
		log.debug("===================");
		log.debug("@Test : getAll()");
		log.debug("===================");
		//1. 매번 동일한 결과가 나오도록한다.
		//2. 전체 삭제
		//3. 데이터 3건 입력
		//4. 전체 조회 = 3건.
		
		dao.deleteAll();
		
		//건수 조회
		int count = dao.selectCnt();
		assertEquals(0, count);

		//3건 등록
		int flag = dao.doSave(userVO01);
		assertEquals(1, flag);
		assertEquals(dao.selectCnt(), 1);
		
		flag=dao.doSave(userVO02);
		assertEquals(1, flag);
		assertEquals(dao.selectCnt(), 2);

		flag=dao.doSave(userVO03);
		assertEquals(1, flag);
		assertEquals(dao.selectCnt(), 3);
		
		// 목록 조회
		List<User> list = dao.getAll();
		assertNotNull(list);
		assertEquals(list.size(), 3);
	}
	
	@Ignore
	@Test
	public void addAndGet() throws SQLException{
		log.debug("===================");
		log.debug("@Test : addAndGet()");
		log.debug("===================");
		//0. 전체 삭제
		//1. 등록
		//2. 한 건 조회
		
		log.debug("───────────────────────");
		log.debug("전체 삭제 : deleteAll() : 3");
		log.debug("───────────────────────");
		int cnt = dao.deleteAll();
		log.debug("cnt : " + cnt);
		//assertEquals(3, cnt);
		
		
		log.debug("───────────────────────");
		log.debug("1번 생성 : doSave() : 1");
		log.debug("───────────────────────");
		int flag = dao.doSave(userVO01);
		log.debug("1.1 등록 flag : " + flag);
		assertEquals(1, flag);
		
		log.debug("───────────────────────");
		log.debug("1번 존재 조회 : selectCnt() : 1");
		log.debug("───────────────────────");
		int count = dao.selectCnt();
		log.debug("1.2 조회 : " + count);
		assertEquals(1, count);
		
		//asertEquals(나와야 되는 값, 실제 값);
		
		log.debug("───────────────────────");
		log.debug("1번 정보 조회 : doSelectOne() : 1");
		log.debug("───────────────────────");
		User outVO = dao.doSelectOne(userVO01);
		assertNotNull(outVO);
		isSameUser(userVO01, outVO);
		
		log.debug("userVO : " + userVO01);
		log.debug("outVO : " + outVO);
		
		//2번째 User
		log.debug("───────────────────────");
		log.debug("2번 생성 : doSave() : 2");
		log.debug("───────────────────────");
		flag = dao.doSave(userVO02);
		log.debug("2.1 등록 flag : " + flag);
		assertEquals(1, flag);
		
		log.debug("───────────────────────");
		log.debug("2번 존재 조회 : selectCnt() : 2");
		log.debug("───────────────────────");
		count = dao.selectCnt();
		log.debug("2.2 조회 : " + count);
		assertEquals(2, count);
		
		log.debug("userVO : " + userVO02);
		
		log.debug("───────────────────────");
		log.debug("2번 정보 조회 : doSelectOne() : 2");
		log.debug("───────────────────────");
		User outVO02 = dao.doSelectOne(userVO02);
		assertNotNull(outVO02);
		isSameUser(userVO02, outVO02);
		
		//3번째 User
		log.debug("───────────────────────");
		log.debug("3번 생성 : doSave() : 1");
		log.debug("───────────────────────");
		flag = dao.doSave(userVO03);
		log.debug("3.1 등록 flag : " + flag);
		assertEquals(1, flag);
		
		log.debug("userVO : " + userVO03);
		log.debug("outVO : " + outVO);
		
		log.debug("───────────────────────");
		log.debug("3번 존재 조회 : selectCnt() : 3");
		log.debug("───────────────────────");
		count = dao.selectCnt();
		log.debug("3.2 조회 : " + count);
		assertEquals(3, count);
		//오브젝트 비교도 가능하다.
		//assertEquals(userVO, outVO);
		
		log.debug("───────────────────────");
		log.debug("3번 정보 조회 : doSelectOne() : 3");
		log.debug("───────────────────────");
		User outVO03 = dao.doSelectOne(userVO03);
		assertNotNull(outVO03);
		isSameUser(userVO03, outVO03);
		
//		log.debug("───────────────────────");
//		log.debug("1번 삭제 : deleteOne()");
//		log.debug("───────────────────────");
//		flag = dao.deleteOne(userVO01);
//		log.debug("flag : " + flag);
//		assertEquals(1, flag);
		
	}
	
	public void isSameUser(User userVO, User outVO) {
		assertEquals(userVO.getUserId(), outVO.getUserId());
		assertEquals(userVO.getName(), outVO.getName());
		assertEquals(userVO.getPassword(), outVO.getPassword());
		assertEquals(userVO.getBirthday(), outVO.getBirthday());
		assertEquals(userVO.getLevel(), outVO.getLevel());
		assertEquals(userVO.getLogin(), outVO.getLogin());
		assertEquals(userVO.getRecommend(), outVO.getRecommend());
		assertEquals(userVO.getEmail(), outVO.getEmail());
	}
	
	@Test
	public void doUpdate()throws SQLException {
		log.debug("===================");
		log.debug("@Test : doUpdate()");
		log.debug("===================");
		//1. 전체삭제
		dao.deleteAll();
		
		int count = dao.selectCnt();
		assertEquals(0, count);
		
		//2. 데이터 1건 입력
		int flag = dao.doSave(userVO01);
		assertEquals(1, flag);
		assertEquals(dao.selectCnt(), 1);
		
		//3. 단건 조회
		User outVO = dao.doSelectOne(userVO01);
		assertNotNull(outVO);
		isSameUser(userVO01, outVO);
		
		//4. 조회 데이터로 수정 및 update
		String updateStr = "_U";
		outVO.setName(outVO.getName());
		outVO.setPassword(outVO.getPassword() + updateStr);
		outVO.setBirthday(outVO.getBirthday());
		 //int 값으로 받아와야함 intValue()
		outVO.setLevel(Level.SILVER);
		outVO.setLogin(outVO.getLogin() + 10);
		outVO.setRecommend(outVO.getRecommend() + 20);
		outVO.setEmail(outVO.getEmail() + updateStr);

		//5. 수정된 데이터 조회
		flag = dao.updateOne(outVO);
		assertEquals(1, flag);
		
		User upOutVO = dao.doSelectOne(outVO);
		
		//6. 비교
		isSameUser(upOutVO, outVO);
		
	}
	
	@Ignore
	@Test
	public void beans() {
		log.debug("=================================");
		log.debug("@Test : beans()");
		log.debug("=================================");
		//null이 아니면 true
		assertNotNull(context);
		assertNotNull(dao);
		log.debug("beans()");
	}
}
