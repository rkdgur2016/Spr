package com.pcwk.ehr;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 "설정 정보" 라는 표시
public class DaoFactory {
	
	@Bean //객체에 Bean 설정 : Object 생성을 담당하는 IoC용 메서드
	public UserDao userDao() {
		
		ConnectionMaker connectionMaker = new NConnectionMaker();
	
		UserDao dao = new UserDao(connectionMaker);
		
		return dao;
	}
	
	@Bean //객체에 Bean 설정 : Object 생성을 담당하는 IoC용 메서드
	public ConnectionMaker connectionMaker() {
		return new NConnectionMaker();
	}
	
}
