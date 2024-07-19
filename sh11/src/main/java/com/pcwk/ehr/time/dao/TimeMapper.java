package com.pcwk.ehr.time.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TimeMapper {
	
	@Select("SELECT sysdate FROM dual") //예시일 뿐임 쓰지 마라.
	public String getDateTime();
	
	
	public String getPcwkDateTime();
	
}
