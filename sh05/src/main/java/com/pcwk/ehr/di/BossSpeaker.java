package com.pcwk.ehr.di;

import com.pcwk.ehr.cmn.PLog;

public class BossSpeaker implements Speaker, PLog {

	
	public BossSpeaker() {
		log.debug("BossSpeaker 생성자");
		
	}

	@Override
	public void playSound() {
		log.debug("play sound from 보스 스피커");
	}

}
