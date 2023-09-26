package com.example.loo.model.member;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class MemberUpdate {
	private String member_mail;
	@NotBlank
	private String password;
	private String member_name;
	@NotBlank
	private String phone;
	private LocalDate birthday;
	private String hire_date;
	private String company_id;
	private String department_id;
	private String position_id;
}
