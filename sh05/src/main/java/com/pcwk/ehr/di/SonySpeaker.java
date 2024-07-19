package com.pcwk.ehr.di;

import com.pcwk.ehr.cmn.PLog;

public class SonySpeaker implements PLog, Speaker{

	
	public SonySpeaker() { 
		log.debug("[SonySpaeker] 생성자");
	}
	
	@Override
	public void playSound() {
		log.debug(">>> play sound Sony Speaker >>>");
	}
	

}
