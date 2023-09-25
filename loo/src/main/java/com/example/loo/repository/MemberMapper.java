package com.example.loo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.loo.model.member.Member;

@Mapper
public interface MemberMapper {
	void saveMember(Member member);
	Member findMember(String company_mail);
}