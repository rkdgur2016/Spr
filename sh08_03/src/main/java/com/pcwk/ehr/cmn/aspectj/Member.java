package com.pcwk.ehr.cmn.aspectj;

public interface Member {
	
	/** 
	 * 단건 저장
	 * @return
	 * */
	int doSave();
	
	/** 
	 * 수정
	 * @return
	 * */
	int doUpdate();
	
	/** 
	 * 삭제
	 * @return
	 * */
	int delete();
	
	/** 
	 * 목록 조회
	 * @return
	 * */
	void doRetrieve(int num);
	
	/** 
	 * 등록
	 * @return
	 * */
	void doInsert(int num) throws IllegalArgumentException;

}
