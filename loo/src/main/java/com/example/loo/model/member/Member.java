package com.example.loo.model.member;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Member {

	private String member_mail;
	private String password;
	private String member_name;
	private String phone;
	private LocalDate birthday;
	private LocalDate hire_date;
	private String company_id;
	private String department_id;
	private String position_id;
	private String department_name;
}