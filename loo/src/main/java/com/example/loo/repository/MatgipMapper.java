package com.example.loo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.loo.model.matgip.Matgip;

@Mapper
public interface MatgipMapper {

	void saveMatgip(Matgip restaurant);
	
	Matgip findMatgip(String matgip_title, String member_mail);
}