package com.pcwk.ehr.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.pcwk.ehr.cmn.PLog;

public class UpperCaseHandler1 implements InvocationHandler,PLog {
	
	Hello target;
	
	
	public UpperCaseHandler1(Hello target) {
		super();
		this.target = target;
	}


	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ @UpperCaseHandler invoke()                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		log.debug("method name : " + method.getName());
		log.debug("args : " + args.toString());
		
		String ret = (String) method.invoke(target, args); //타겟으로 위임, 인터페이스 메서드
		
		log.debug("ret : " + ret);
		return ret.toUpperCase();
	}

}
