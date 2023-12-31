package com.example.loo.model.member;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class MemberUpdate {
	private String member_mail;
	@NotBlank(message = "새 비밀번호를 입력해 주세요.")
	@Length(min = 4, max = 20, message = "비밀번호는 4 ~ 20자내로 입력해 주세요.")
	private String password;
	private String member_name;
	@NotBlank(message = "연락처를 입력해 주세요.")
	private String phone;
	private LocalDate birthday;
	private String hire_date;
	private String company_id;
	private String department_id;
	private String position_id;
	private String saved_filename;
}