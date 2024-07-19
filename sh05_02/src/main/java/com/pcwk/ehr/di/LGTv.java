package com.pcwk.ehr.di;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pcwk.ehr.cmn.PLog;

@Component("lgTv")
public class LGTv implements Tv, PLog {

	final String brand = "LG Tv";
	
	@Resource(name = "bossSpeaker")
	private Speaker speaker;
	
	private double price;
	
	
	public LGTv() {
		log.debug("[LGTV]");
		
	}
	
	public LGTv(Speaker speaker, double price) {
		super();
		this.speaker = speaker;
		this.price = price;
	}


	@Override
	public void turnOn() {
		log.debug(brand+"turnOn");
		log.debug(price);
	}

	@Override
	public void turnOff() {
		log.debug(brand+"turnOff");
	}

	@Override
	public void playSound() {
		log.debug("<<< LGTv play Sound <<<");
		speaker.playSound();

	}

}
