package com.example.loo.model.member;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class MemberLogin {
		@NotBlank
		private String company_mail;
		@NotBlank
		private String password;
}
