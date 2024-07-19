package com.pcwk.ehr.di;

import org.springframework.stereotype.Component;

import com.pcwk.ehr.cmn.PLog;

@Component("bossSpeaker")
public class BossSpeaker implements Speaker, PLog {

	
	public BossSpeaker() {
		log.debug("BossSpeaker 생성자");
		
	}

	@Override
	public void playSound() {
		log.debug("play sound from 보스 스피커");
	}

}
