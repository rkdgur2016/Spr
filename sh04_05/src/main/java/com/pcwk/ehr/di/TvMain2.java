package com.pcwk.ehr.di;

public class TvMain2 {
	
	public static void main(String[] args) {
		
		SamsungTv samsungTv = new SamsungTv();
		samsungTv.powerOn();
		samsungTv.powerOff();
		samsungTv.volumeDown();
		samsungTv.volumeUp();
	}
}
