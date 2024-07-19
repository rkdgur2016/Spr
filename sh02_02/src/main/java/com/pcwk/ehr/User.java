package com.pcwk.ehr;

public class User {
	
	private String userId ;	//ID
	private String name    ;	//이름
	private String password;	//비밀번호
	private String birthday;	//생년월일
	
	public User() {
		super();
	}

	public User(String user_id, String name, String password, String birthday) {
		super();
		this.userId = user_id;
		this.name = name;
		this.password = password;
		this.birthday = birthday;
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

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", password=" + password + ", birthday=" + birthday + "]";
	}

	
}
