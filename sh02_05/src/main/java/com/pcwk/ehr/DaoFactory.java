package com.pcwk.ehr;

public class DaoFactory {
	
	public UserDao userDao() {
		
		ConnectionMaker connectionMaker = new NConnectionMaker();
	
		UserDao dao = new UserDao(connectionMaker);
		
		return dao;
	}

	
//BoardDao가 생기면 
//	public BoardDao boardDao() {
//			
//		ConnectionMaker connectionMaker = new NConnectionMaker();
//	
//		BoardDao dao = new BoardDao(connectionMaker);
//		
//		return dao;
//	}
	
	public ConnectionMaker connectionMaker() {
		return new NConnectionMaker();
	}
	
//connectionMaker() 함수 생성 후 사용
//	public UserDao userDao() {
//		
//		UserDao dao = new UserDao(connectionMaker());
//		
//		return dao;
//	}
}
