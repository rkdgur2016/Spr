/**
 * Package Name : com.pcwk.ehr <br/>
 * Class Name: User.java <br/>
 * Description: 회원 Value Object <br/>
 * Modification information : <br/>
 * ------------------------------------------<br/>
 * 최초 생성일 : 2024.07.03
 * 			   2024.07.03 : 유저 Level 기능 추가
 *
 * ------------------------------------------<br/>
 * @author : user
 * @since  : 2023.09.07
 * @version: 0.5
 */
package com.pcwk.ehr;

public class User {
	
	private String userId ;		//ID
	private String name    ;	//이름
	private String password;	//비밀번호
	private String birthday;	//생년월일
	
	// 2024. 07. 03 추가
	
	private Level level;		//등급
	private int login;			//로그인
	private int recommend;		//추천
	private String email;		//이메일
	private String regDt;		//등록일
	
	
	public User() {
		super();
	}

	public User(String userId, String name, String password, String birthday, Level level, int login, int recommend,
			String email, String regDt) {
		super();
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.birthday = birthday;
		this.level = level;
		this.login = login;
		this.recommend = recommend;
		this.email = email;
		this.regDt = regDt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", password=" + password + ", birthday=" + birthday
				+ ", level=" + level + ", login=" + login + ", recommend=" + recommend + ", email=" + email + ", regDt="
				+ regDt + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
