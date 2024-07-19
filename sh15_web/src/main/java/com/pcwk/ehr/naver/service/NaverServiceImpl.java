package com.pcwk.ehr.naver.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.Search;

@Service
public class NaverServiceImpl implements NaverService,PLog  {
	
	//1. Naver Web 인증
	//2. 검색어 : UTF-8
	//3. Json 형태로 받기
	
	String clientId = "15CAgSQHklqF_lth0pGp";
	String clientSecret = "tYYjMKZWzw";
	
	String apiURL = "https://openapi.naver.com/v1/search/blog.json?query=";
	
	public NaverServiceImpl() {}
	
	public String doNaverSearch(Search search) throws UnsupportedEncodingException {
		String searchWord = "";
		
		searchWord = URLEncoder.encode(search.getSearchWord(), "UTF-8");
		log.debug("searchWord : " +searchWord);
		
		
		return "";
	}
}
