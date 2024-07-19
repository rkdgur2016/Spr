/**
 * Package Name : com.pcwk.ehr.code.service <br/>
 * Class Name: CodeServiceImpl.java <br/>
 * Description: <br/>
 * Modification imformation :
 * ------------------------------------------
 * 최초 생성일 : 2024.07.19
 *
 * ------------------------------------------
 * author : acorn
 * since  : 2020.11.23
 * version: 0.5
 * see    : <br/>
 * Copyright (C) by KandJang All right reserved.
 */
package com.pcwk.ehr.code.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.code.domain.Code;
import com.pcwk.ehr.mapper.CodeMapper;

/**
 * @author acorn
 *
 */
@Service
public class CodeServiceImpl implements CodeService,PLog {

	@Autowired
	CodeMapper codeMapper;
	
	public CodeServiceImpl() {
		log.debug("┌───────────────────────────┐");
		log.debug("│ CodeServiceImpl()         │");
		log.debug("└───────────────────────────┘");
	};
	
	@Override
	public List<Code> doRetrieve(DTO search) throws SQLException {
		log.debug("┌───────────────────────────────────────────┐");
		log.debug("│ CodeServiceImpl : doRetrieve()            │");
		log.debug("└───────────────────────────────────────────┘");
		
		Code inVO = (Code) search;
		log.debug("┌");
		log.debug("│ 1. param : " + inVO);
		log.debug("└");
		
		List<Code> list = codeMapper.doRetrieve(inVO);
		
		log.debug("┌──────────────────────────");
		for(Code vo : list) {
			log.debug(vo);
		}
		log.debug("└──────────────────────────");
		return list;
	}

	@Override
	public int doUpdate(Code inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(Code inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doSave(Code inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Code doSelectOne(Code inVO) throws SQLException, NullPointerException {
		
		return null;
	}

	@Override
	public List<Code> doRetrieveIn(List<String> codeList) throws SQLException {
		log.debug("┌───────────────────────────────────────────┐");
		log.debug("│ CodeServiceImpl : doRetrieveIn()          │");
		log.debug("└───────────────────────────────────────────┘");
		log.debug("┌");
		log.debug("│ 1. param : " + codeList);
		log.debug("└");
		
		List<Code> list = codeMapper.doRetrieveIn(codeList);
		
		log.debug("┌──────────────────────────");
		for(Code vo : list) {
			log.debug(vo);
		}
		log.debug("└──────────────────────────");
		
		return list;
		
	}

}
