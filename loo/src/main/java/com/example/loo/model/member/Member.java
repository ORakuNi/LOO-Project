package com.example.loo.model.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Member {
	private Long member_id;
	private String company_mail;
	private String password;
	private String member_name;
	private String phone;
	private LocalDate birthday;
	private String hire_date;
	private String company_id;
	private String department_id;
	private String position_id;
}