package com.pcwk.ehr.code.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.code.domain.Code;

public interface CodeService extends WorkDiv<Code> {

	// doRetrieveIn
	/*
	 * @param codeList(COM_PAGE_SIZE, MEMBER_SEARCH)
	 * @return
	 * */
	public List<Code> doRetrieveIn(List<String> codeList) throws SQLException;
}
