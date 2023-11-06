package com.example.loo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.loo.model.matgip.Matgip;

@Mapper
public interface MatgipMapper {

	void saveMatgip(Matgip matgip);
	
	List<Matgip> findMatgip(String member_mail);
	
	Matgip findMatgipTitle(@Param("matgip_title")String matgip_title, @Param("member_mail")String member_mail);
	
	
	Matgip findMatgipByMail(@Param("matgip_num")Long matgip_num , @Param("member_mail")String member_mail);
	
	// @Param 어노테이션에 각각 구분해서 안넣어주면 spring에서 인식못함  
	void removeMatgip(@Param("matgip_title")String matgip_title, @Param("member_mail")String member_mail);
	
}