package com.pcwk.ehr.local;

public class AnnonymousInnerClass {
	
	public interface Greeting{
		void greet();
	}
	
	public void sayHello() {
		//Greeting 인터페이스의 인스턴스 생성
		
		Greeting greeting = new Greeting() {
			
			public void greet() {
				
				System.out.println("익명 이너 클래스");
			}
		};
		
		//익명 innter class 호출
		greeting.greet();
	}
	
	public static void main(String[] args) {
		AnnonymousInnerClass main = new AnnonymousInnerClass();
		main.sayHello();
	}
}
