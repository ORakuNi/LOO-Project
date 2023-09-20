package com.example.loo.model.member;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class Member {
	private String member_id;
	private String company_mail;
	private String password;
	private String name;
	private String phone_number;
	private String birthday;
	private LocalDateTime hire_date;
	private String company_id;
	private String department_id;
	private String position_id;
	
}
