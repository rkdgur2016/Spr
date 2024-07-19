package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.code.domain.Code;

@Mapper
public interface CodeMapper extends WorkDiv<Code> {
	
	/** 
	 * 파라미터로 list를 받는 메서드
	 * */
	List<Code> doRetrieveIn(List search) throws SQLException;

}
