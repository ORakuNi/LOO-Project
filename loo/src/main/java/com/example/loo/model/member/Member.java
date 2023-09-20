package com.example.loo.model.member;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Member {
	private Long member_id;
	private String company_mail;
	private String password;
	private String name;
	private String phone_number;
	private LocalDate birthday;
	private LocalDate hire_date;
	private String company_id;
	private String department_id;
	private String position_id;
}
