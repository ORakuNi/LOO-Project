package com.example.loo.model.member;

public enum Position {
	manager("매니저"),
	employee("사원");
	
	private String description;
	
	private Position(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}	
}
