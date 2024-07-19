package com.pcwk.ehr.di02;

public class BeanFactory {
	
	public Object getBean(String beanName) {
		
		if(beanName.equals("samsung")) {
			return new SamsungTv();
		}else if(beanName.equals("lg")) {
			return new LgTv();
		}
		
		return null;
	}
}
