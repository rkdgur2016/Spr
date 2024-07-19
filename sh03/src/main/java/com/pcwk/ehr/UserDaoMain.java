
package com.pcwk.ehr;

import java.sql.SQLException;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericXmlApplicationContext;

public class UserDaoMain {
	
	UserDao dao;
	User user;
	ApplicationContext context;
	
	public UserDaoMain() {
		
		//DaoFactory의 @Configuration이 붙은 자바 코드를 설정정보로 사용하려면 AnnotationConfigApplicationContext를 사용
		context = new GenericXmlApplicationContext("applicationContext.xml");
		System.out.println("context : " + context);
		
		//객체 생성
		//dao = new DaoFactory().userDao();
		
		dao = context.getBean("userDao", UserDao.class);
		System.out.println("dao : " + dao);
		
		user = new User("human", "사람", "1111", "2001-03-16");
	}
	
	public void doSave() {
		System.out.println("doSave");
		
		try {
			//doSave() return = int flag
			int flag= dao.doSave(user);
			
			if(1== flag) {
				System.out.println("성공");
			}else {
				System.out.println("실패");
			}
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void doSelectOne() {
		System.out.println("doSelectOne");
		
		try {
			
			//doSelectOne return = User;
			User outVO = dao.doSelectOne(user);
			if (null != outVO) {
				System.out.println("성공");
			} else {
				System.out.println("실패");
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		UserDaoMain main = new UserDaoMain();
		
		//main.doSave();
		main.doSelectOne();
	}
}
