package com.pcwk.ehr.sync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.sync.domain.Sync;

@Controller
public class SyncController implements PLog{
	public SyncController() {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ SyncController()");
		log.debug("└─────────────────────────────────────────────────────────");	
	}
	
	// /WEB-INF/views/sync/sync_index.jsp
	// /WEB-INF/views/sync/sync_result.jsp
	
	@RequestMapping(value = "/sync/sync_index.do", method = RequestMethod.GET)
	public String handlerSyncIndex() {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ handlerSyncIndex()");
		log.debug("└─────────────────────────────────────────────────────────");
		
		String viewName =  "sync/sync_index";
		log.debug("viewName : " + viewName);
		return viewName;			
	}
	
	@RequestMapping(value = "/sync/sync_result.do", method = RequestMethod.GET)
	public ModelAndView syncResult(Sync sync, ModelAndView modelAndView) {	
		
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ syncResult()");
		log.debug("└─────────────────────────────────────────────────────────");
		
		log.debug("sync : " + sync);
		log.debug("sync : " + sync.getName());
		String viewName =  "sync/sync_result";
		log.debug("viewName : " + viewName);
		
		// 데이터를 화면에 전달
		modelAndView.addObject("message", "Hi" + sync.getName());

		// 화면 전달
		modelAndView.setViewName(viewName);
		
		return modelAndView;		
	}
	
// 	다른 방식	
//	@RequestMapping(value = "/sync/sync_result.do", method = RequestMethod.GET)
//	public String syncResult(@RequestParam("name") String name) {		
//		log.debug("┌─────────────────────────────────────────────────────────");
//		log.debug("│ syncResult()");
//		log.debug("└─────────────────────────────────────────────────────────");
//		
//		log.debug("name : " + name);		
//		String viewName =  "/sync/sync_result";
//		log.debug("viewName : " + viewName);
//		return viewName;		
//	}
	
}
