package com.pcwk.ehr;

public enum Level {
	
	//BASIC, SILVER, GOLD
	BASIC(1),SILVER(2),GOLD(3);
	
	//변수
	private final int value;
	
	//생성자
	Level(int value){
		this.value = value;
	}
	
	//value 값 확인 함수
	public int intValue() {
		return value;
	}
	
	//value 값으로 레벨을 받는 함수
	public static Level valueOf(int value) {
		
		switch(value) {
		case 1 :
			return BASIC;
		case 2 :
			return SILVER;
		case 3 :
			return GOLD;
		default :
			throw new AssertionError("Unknown value : " + value);
		}
	}
	
}
