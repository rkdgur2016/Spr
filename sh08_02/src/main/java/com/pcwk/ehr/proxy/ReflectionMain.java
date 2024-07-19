package com.pcwk.ehr.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.pcwk.ehr.cmn.PLog;

public class ReflectionMain implements PLog{

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, IllegalAccessException {
		
		
		//기존 방식 vs 리플랙션
		
		//기존 방식
		String name = "Spring";
		System.out.println("name의 문자열 길이 : " + name.length());
		System.out.println("name의 문자 : " + name.charAt(0));
		
		//리플랙션
		//모든 자바 클래스는 Class 타입의 오브젝트를 가지고 있다.
		Method lengthMethod = String.class.getMethod("length");
		int nameLength = (int)lengthMethod.invoke(name, args);
		
		System.out.println("리플랙션 길이 : " + nameLength);
		
		//리플랙션, 인자 전달
		Class reflectionClass = String.class;
		
		Method charAtMethod = reflectionClass.getMethod("charAt" , int.class);
		System.out.println("name의 첫번째 문자열 : " + charAtMethod.invoke(name, 0));
	}
}
