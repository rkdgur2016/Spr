package com.pcwk.ehr.sync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.sync.domain.Sync;

@Controller
public class SyncController implements PLog{
	
	public SyncController() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ SyncController()                         │");
		log.debug("└──────────────────────────────────────────┘");
		
	}

	
	@RequestMapping(value="/sync/sync_index.do", method= RequestMethod.GET)
	public String handlerSyncIndex() {
		//http://localhost:8080/ehr/sync/sync_index.do
		
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ handlerSyncIndex()                       │");
		log.debug("└──────────────────────────────────────────┘");
		
		//prefix + viewName + suffix -> //http://localhost:8080/ehr/sync/sync_index.do
		String viewName = "sync/sync_index";
		log.debug("viewName : " + viewName);
		
		return viewName;
	}
	
	@RequestMapping(value="/sync/sync_result.do", method=RequestMethod.GET)
	public ModelAndView SyncResult(Sync sync, ModelAndView modelAndView) {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ SyncResult()                      		  │");
		log.debug("└──────────────────────────────────────────┘");
		
		//parameter에 있는 name을 가져온다.
//		String name = req.getParameter("name");
		log.debug("sync : " + sync);
		
		String viewName ="sync/sync_result";
		log.debug("viewName : " + viewName);
		
		//데이터를 화면에 전달
		modelAndView.addObject("message", "Hi!" +  sync.getName());
		modelAndView.setViewName(viewName);
		
		return modelAndView;
	}
}
