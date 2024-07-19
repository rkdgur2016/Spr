package com.pcwk.ehr.cmn;

/**
 * 검색 조건
 * @author acorn
 *
 */
public class Search extends DTO {

	private String searchWord; //검색어
	private String searchDiv; //검색구분
	
	public Search() {
		
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getSearchDiv() {
		return searchDiv;
	}

	public void setSearchDiv(String searchDiv) {
		this.searchDiv = searchDiv;
	}

	@Override
	public String toString() {
		return "Search [searchWord=" + searchWord + ", searchDiv=" + searchDiv + ", toString()=" + super.toString()
				+ "]";
	}
	
}
