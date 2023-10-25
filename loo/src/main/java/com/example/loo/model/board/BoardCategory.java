package com.example.loo.model.board;


public enum BoardCategory {
	NOTICE("공지 게시판"),
	FREE("게시판"),
	CLUB("동호회 게시판"),
	REPORT("업무보고 게시판");
	
	private String description;
	
	private BoardCategory(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}	
}
