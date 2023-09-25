package com.example.loo.model.member;


import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


import lombok.Data;

@Data
public class MemberJoinForm {
	
	private Long member_id;
	@NotBlank
	private String company_mail;
	@NotBlank
	private String password;
	@NotBlank
	private String member_name;
	@NotBlank
	private String phone;
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	private String hire_date;
	private String company_id;
	private String department_id;
	private String position_id;
	
	public static Member toMember(MemberJoinForm memberJoinForm) {
		Member member = new Member();
		
		member.setMember_id(memberJoinForm.getMember_id());
		member.setCompany_mail(memberJoinForm.getCompany_mail());
		member.setPassword(memberJoinForm.getPassword());
		member.setMember_name(memberJoinForm.getMember_name());
		member.setPhone(memberJoinForm.getPhone());
		member.setBirthday(memberJoinForm.getBirthday());
		member.setHire_date(memberJoinForm.getHire_date());
		member.setCompany_id(memberJoinForm.getCompany_id());
		member.setDepartment_id(memberJoinForm.getDepartment_id());
		member.setPosition_id(memberJoinForm.getPosition_id());
		
		return member;
	}

}
