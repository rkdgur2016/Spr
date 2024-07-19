package com.pcwk.ehr.di;

import com.pcwk.ehr.cmn.PLog;

public class SamsungTv implements PLog{
	
	/*
	 * powerOn()
	 * powerOff()
	   volumeUp()
	   volumeDown()
	 * */
	
	public SamsungTv() {
		log.debug("SamsungTv 생성자 호출!");
	}
	final String brand = "삼성TV";
	
	public void powerOn() {
		log.debug(brand + "의 전원이 켜집니다.");
	}
	
	public void powerOff() {
		log.debug(brand + "의 전원이 꺼집니다.");	
	}
	
	public void volumeUp() {
		log.debug(brand + "의 볼륨이 커집니다.");
	}
	
	public void volumeDown() {
		log.debug(brand + "의 볼륨이 낮아집니다.");
	}
	
}
