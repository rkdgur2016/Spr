package com.pcwk.ehr.async.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcwk.ehr.cmn.PLog;

@Controller
public class AsyncController implements PLog{
	
	public AsyncController() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ AsyncController()                        │");
		log.debug("└──────────────────────────────────────────┘");
	}
	
	@GetMapping("/async/async_index.do") //requestMapping + Method="get"
	public String asyncIndex() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ asyncIndex()                        	  │");
		log.debug("└──────────────────────────────────────────┘");
		String viewName = "async/async_index";
		log.debug("viewName : " + viewName);
		
		return viewName;
	}
	
	@RequestMapping(value="/async/async.do", method= RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String async(@RequestParam("name") String name) {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ async()                        	  	  │");
		log.debug("└──────────────────────────────────────────┘");
		
		log.debug("name : " + name);
		
		return "Hello " + name;
	}
	
	@RequestMapping(value="/async/asyncJs.do", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String asyncJs(HttpServletRequest req) {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ asyncJs()                        	  	  │");
		log.debug("└──────────────────────────────────────────┘");
		
		String name = req.getParameter("name");
		log.debug("name : " +name);
		
		return "Hello asyncJs " + name;
	}
}
