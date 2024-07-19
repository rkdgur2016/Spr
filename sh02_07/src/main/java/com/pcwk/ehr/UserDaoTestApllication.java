package com.pcwk.ehr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class UserDaoTestApllication {

	UserDao dao;
	User userVO;
	
	ApplicationContext context;
	
	public UserDaoTestApllication() {
		//sh02_07\src\main\java\applicationContext.xml
		//GenericXmlApplicaionContext = xml파일을 읽어오는 메서드이다.
		context = new GenericXmlApplicationContext("applicationContext.xml");
		System.out.println("1. context : " + context);
		
		dao = context.getBean("userDao", UserDao.class);
		System.out.println("2. dao : " + dao);
	}
	
	
	public static void main(String[] args) {
		
		UserDaoTestApllication main = new UserDaoTestApllication();
	}

}
