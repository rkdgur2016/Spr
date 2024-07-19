
package com.pcwk.ehr;

import java.sql.SQLException;

public class UserDaoMain {
	
	UserDao dao;
	User user;
	
	public UserDaoMain() {
		dao = new UserDao();
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
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
