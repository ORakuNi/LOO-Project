package com.example.loo.model.member;

public enum Position {
	manager("매니저"),
	employee("사원");
	
	// enum 만들어 두긴 했는데 안 쓰는 중 삭제해도 무방 
	
	private String description;
	
	private Position(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}	
}
