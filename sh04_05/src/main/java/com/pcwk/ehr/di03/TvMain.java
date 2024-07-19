package com.pcwk.ehr.di03;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.di03.Tv;

public class TvMain {
	
	public static void main(String[] args) {
		//인터페이스 이용해서 호출 가능	
		
		ApplicationContext context = new GenericXmlApplicationContext("/com/pcwk/ehr/di03/TvApplictaionContext.xml");
		Tv tv =(Tv)context.getBean("tv");
		
		tv.powerOn();
		tv.powerOff();
		tv.volumeUp();
		tv.volumeDown();
	}
	
	
}
