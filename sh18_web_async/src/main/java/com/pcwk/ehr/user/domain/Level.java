package com.pcwk.ehr.user.domain;

/*
 * 사용자 등급 : BASIC, SILVER, GOLD
 * @author acorn
 * */
public enum Level {
	// BASIC, SILVER, GOLD 높은 단계가 앞에 있어야 undefined 오류가 발생하지 않는다. 낮은 단계에 높은 단계값이 있기때문에 그 보다 앞에 높은 단계값을 정의해줘야 하기 때문이다
	GOLD(3,null), SILVER(2,GOLD), BASIC(1,SILVER);
	
	private final int value;
	private final Level next; // 다음 Level 정보
	
	Level(int value, Level next){
		this.value = value;
		this.next  = next;
	}
	
	public int intValue() {
		return value;
	}
	
	public Level nextLevel() {		
		return this.next;
	}
	
	// 값으로 부터 level 오브젝트 Return
	public static Level valueOf(int value) {
		switch (value) {
		case 1:
			return BASIC;
		case 2:
			return SILVER;
		case 3:
			return GOLD;
		default:
			throw new AssertionError("모르겠고 오류다" + value);
		}
		
	}
	
} // enum end
