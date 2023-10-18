package com.example.loo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.loo.model.member.Member;

@Mapper
public interface PhonesMapper {
	
	List<Member> findAllPhones();
	
	List<Member> findMember(@Param("member_name") String member_name, @Param("member_mail") String member_mail, 
			@Param("phone") String phone, @Param("department_name") String department_name);

}
