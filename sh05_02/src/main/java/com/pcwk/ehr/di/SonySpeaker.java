package com.pcwk.ehr.di;

import org.springframework.stereotype.Component;

import com.pcwk.ehr.cmn.PLog;

@Component("sonySpeaker")
public class SonySpeaker implements PLog, Speaker{

	
	public SonySpeaker() { 
		log.debug("[SonySpaeker] 생성자");
	}
	
	@Override
	public void playSound() {
		log.debug(">>> play sound Sony Speaker >>>");
	}
	

}
