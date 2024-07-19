package com.pcwk.ehr.di03;

import com.pcwk.ehr.cmn.PLog;

public class LgTv implements PLog, Tv {
	
	final String brand = "LG TV";
	
	@Override
	public void powerOn() {
		log.debug(brand + "의 전원이 켜집니다.");

	}

	@Override
	public void powerOff() {
		log.debug(brand + "의 전원이 꺼집니다.");	

	}

	@Override
	public void volumeUp() {
		log.debug(brand + "의 볼륨이 커집니다.");

	}

	@Override
	public void volumeDown() {
		log.debug(brand + "의 볼륨이 낮아집니다.");

	}

}
