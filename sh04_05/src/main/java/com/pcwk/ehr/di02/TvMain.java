package com.pcwk.ehr.di02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.pcwk.ehr.cmn.PLog;

public class TvMain implements PLog{
	
	public static void main(String[] args) {
		//인터페이스 이용해서 호출 가능
		
		//BeanFactory factory = new BeanFactory();
		//xml 파일 읽을 때는 GenericXmlApplicationContext(파일주소)
		ApplicationContext context = new GenericXmlApplicationContext("/com/pcwk/ehr/di03/TvApplictaionContext.xml");
		
		Tv tv =(Tv)context.getBean("tv");
		
		tv.powerOn();
		tv.powerOff();
		tv.volumeUp();
		tv.volumeDown();
	}
	
	
}
