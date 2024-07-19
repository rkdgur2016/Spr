package com.pcwk.ehr.proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Proxy;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.pcwk.ehr.cmn.PLog;

public class HelloProxyTest implements PLog{

	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ @Before setUp()                                 		 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ @@After tearDown()                                 	 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}
	
	@Test
	public void dynamicProxy() {
		//다이나믹 프록시
		//런타임시 동적으로 만들어지는 오브젝트
		//리플렉션을 이용해서 프록시 생성
		Hello dynamicProxiedHello = (Hello) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {Hello.class}, new UpperCaseHandler(new HelloTarget()));
		
		log.debug(dynamicProxiedHello.sayHello("Pcwk"));
		log.debug(dynamicProxiedHello.sayHi("Pcwk"));
		log.debug(dynamicProxiedHello.sayThankYou("Pcwk"));
	}
	
	@Ignore
	@Test
	public void proxyHello() {
		
		//HelloTarget으로 동작한다.
		Hello proxyedHello = new HelloUpperCase(new HelloTarget());
		
		//1. toUpperCase() 반복적으로 나온다.
		//2. Hello 인터페이스의 모든 메서드를 구현해야 한다.
		log.debug(proxyedHello.sayHello("Pcwk"));
		log.debug(proxyedHello.sayHi("Pcwk"));
		log.debug(proxyedHello.sayThankYou("Pcwk"));
		
		assertEquals(proxyedHello.sayHello("Pcwk"), "HELLO PCWK");
		assertEquals(proxyedHello.sayHi("Pcwk"), "HI PCWK");
		assertEquals(proxyedHello.sayThankYou("Pcwk"), "THANK YOU PCWK");
	}
	
	@Ignore
	@Test
	public void test() {
		Hello hello = new HelloTarget();
		log.debug("hello.sayHello(\"pcwk\") : " + hello.sayHello("Pcwk"));
		assertEquals(hello.sayHello("Pcwk"), "Hello Pcwk");
		assertEquals(hello.sayHi("Pcwk"), "Hi Pcwk");
		assertEquals(hello.sayThankYou("Pcwk"), "Thank You Pcwk");
		
	}

}
