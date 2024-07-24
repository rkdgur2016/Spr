package com.pcwk.ehr.naver.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.naver.domain.Channel;
import com.pcwk.ehr.naver.domain.Item;

@Service
public class NaverServiceImpl implements NaverService, PLog {
	// 1. naver web 인증
	String clientId = "nV6BDxPROUPvgXr7HwFZ";
	String clientSecret = "o6ZV3i4Kjn";
	String apiURL = "https://openapi.naver.com/v1/search/blog.json?query=";
	
	public NaverServiceImpl() {
		
	}
	
	// 2, 검색어 : UTF-8
	public String doNaverSearch(Search search) throws IOException {
		String searchWord = ""; // 검색어를 저장
		searchWord = URLEncoder.encode(search.getSearchWord(), "UTF-8");
		apiURL += searchWord;
		log.debug("searchWord : " + searchWord);
		// apiURL = "https://openapi.naver.com/v1/search/blog.json?query=";
		log.debug("apiURL : " + apiURL);
		
		// Naver 인증 요청
		URL url = new URL(apiURL);
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		// Clinet ID 인증
		con.setRequestMethod("GET");
		con.setRequestProperty("X-Naver-Client-Id", clientId);
		con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
		
		// 응답코드 : 200 성공
		int responseCode = con.getResponseCode();
		log.debug("responseCode : " + responseCode);

		
	// 3. JSON 형태로 받기
		String retString = "";
		// 접속 성공
		if (200 == responseCode) {
			retString = readBody(con.getInputStream());
			
			// json -> Object로 변환
			Gson gson = new Gson();
			Channel channel = gson.fromJson(retString, Channel.class);
			
			log.debug(retString);
			for (Item item : channel.getItems()) {
				log.debug(item.toString());
			}
			
		// 접속 실패 else
		}else {
			log.debug("접속 실패입니다 responseCode : " + responseCode);
			
		}
		
		return retString;
	}
	
	private static String readBody(InputStream is) {
		StringBuilder sb = new StringBuilder(1000);
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
			String inputData = "";
			
			while ((inputData = br.readLine()) != null) {
				sb.append(inputData + "\n");
			}
			log.debug(sb.toString());
		} catch (IOException e) {
			log.debug("IOException : " + e.getMessage());
			e.printStackTrace();
		}
		
		return sb.toString();
	} // readBody		
	
}
