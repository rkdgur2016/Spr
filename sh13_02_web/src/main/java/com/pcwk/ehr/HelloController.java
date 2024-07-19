package com.pcwk.ehr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	@RequestMapping(value= "/hello.do")
	public String hello(Model model) {
		model.addAttribute("message", "Hello, Spring MVC");
		
		/*
		 * servlet-context.xml에서 
		 * <beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
			<beans:property name="prefix" value="/WEB-INF/views/" />
			<beans:property name="suffix" value=".jsp" />
			</beans:bean>
			주소를 만든다.
		 * */
		// WEB-INF/views/hello.jsp
		return "hello";
	}
	
}
