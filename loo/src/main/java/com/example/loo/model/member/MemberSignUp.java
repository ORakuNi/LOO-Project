package com.example.loo.model.member;


import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class MemberSignUp {
	@NotBlank(message = "아이디를 입력해 주세요.")
	@Email(message = "이메일 주소를 정확히 기재해 주세요.")
	private String member_mail;
	@NotBlank(message = "비밀번호를 입력해 주세요.")
	@Length(min = 4, max = 20, message = "비밀번호는 8 ~ 20자내로 입력해 주세요.")
	private String password;
	@NotBlank(message = "이름을 입력해 주세요.")
	private String member_name;
	@NotBlank(message = "연락처를 입력해 주세요.")
	private String phone;
	@NotNull(message = "생년월일을 입력해 주세요.")
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	@PastOrPresent
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate hire_date;
	@NotEmpty(message = "회사를 선택해 주세요.")
	private String company_id;
	@NotEmpty(message = "부서 정보를 선택해 주세요.")
	private String department_id;
	@NotEmpty(message = "직책을 선택해 주세요.")
	private String position_id;
	
	public static Member toMember(MemberSignUp memberJoinForm) {
		Member member = new Member();
		
		member.setMember_mail(memberJoinForm.getMember_mail());
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
