package com.example.loo.model.member;

import java.time.LocalDate;

import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MemberAdminUpdate {

	private String member_mail;
	private String member_name;
	private String phone;
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	@PastOrPresent
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate hire_date;
	private String company_id;
	private String department_id;
	private String position_id;
	
	
	public static Member toMember(MemberAdminUpdate memberAdminUpdate) {	
		Member member = new Member();
		
		member.setMember_name(memberAdminUpdate.getMember_name());
		member.setPhone(memberAdminUpdate.getPhone());
		member.setBirthday(memberAdminUpdate.getBirthday());
		member.setHire_date(memberAdminUpdate.getHire_date());
		member.setCompany_id(memberAdminUpdate.getCompany_id());
		member.setDepartment_id(memberAdminUpdate.getDepartment_id());
		member.setPosition_id(memberAdminUpdate.getPosition_id());
		
		return member;
	}
	
}
