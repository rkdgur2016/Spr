package com.pcwk.ehr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingleTonMain {
	
	public static void main(String[] args) {
		
		DaoFactory factory = new DaoFactory();
		
		UserDao userDao01 = factory.userDao();
		System.out.println("===========================");
		System.out.println("userDao01 : " + userDao01);
		System.out.println("===========================");
		
		UserDao userDao02 = factory.userDao();
		System.out.println("===========================");
		System.out.println("userDao01 : " + userDao02);
		System.out.println("===========================");
		
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		
		UserDao userDao03 = context.getBean("userDao", UserDao.class);
		UserDao userDao04 = (UserDao)context.getBean("userDao");
		
		if(userDao01 == userDao02) {
			System.out.println("userDao01 == userDao02");
		}else {
			System.out.println("userDao01 != userDao02");
		}
		
		if(userDao03 == userDao04) {
			System.out.println("userDao03 == userDao04");
		}else {
			System.out.println("userDao03 != userDao04");
		}
	}
}
