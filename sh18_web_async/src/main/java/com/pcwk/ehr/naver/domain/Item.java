package com.pcwk.ehr.naver.domain;

public class Item {
	private String title; // 제목
	private String link; // 링크
	private String description; // 상세 설명
	private String bloggername; // 블로거 이름
	private String bloggerlink; // 블로거 링크
	private String postdate; // 글 등록일
	
	public Item() {
	} // 깡통

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBloggername() {
		return bloggername;
	}

	public void setBloggername(String bloggername) {
		this.bloggername = bloggername;
	}

	public String getBloggerlink() {
		return bloggerlink;
	}

	public void setBloggerlink(String bloggerlink) {
		this.bloggerlink = bloggerlink;
	}

	public String getPostdate() {
		return postdate;
	}

	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}

	@Override
	public String toString() {
		return "item [title=" + title + ", link=" + link + ", description=" + description + ", bloggername="
				+ bloggername + ", bloggerlink=" + bloggerlink + ", postdate=" + postdate + ", toString()="
				+ super.toString() + "]";
	}
	
}
