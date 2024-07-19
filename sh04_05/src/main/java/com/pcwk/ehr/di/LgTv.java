package com.pcwk.ehr.di;

import com.pcwk.ehr.cmn.PLog;

public class LgTv implements PLog{
	
	/*
	 * powerOn()
	 * powerOff()
	   volumeUp()
	   volumeDown()
	 * */
	
	public LgTv() {
		log.debug("LG tv 생성자 호출!");
	}
	final String brand = "LG tv";
	
	public void turnOn() {
		log.debug(brand + "의 전원이 켜집니다.");
	}
	
	public void turnOff() {
		log.debug(brand + "의 전원이 꺼집니다.");	
	}
	
	public void soundUp() {
		log.debug(brand + "의 볼륨이 커집니다.");
	}
	
	public void soundDown() {
		log.debug(brand + "의 볼륨이 낮아집니다.");
	}
	
}
