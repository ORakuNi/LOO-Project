package com.example.loo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.loo.model.restaurant.Restaurant;

@Mapper
public interface MatgipMapper {

	void saveMatgip(Restaurant restaurant);
	
	Restaurant findMatgip(String matgip_title, String member_mail);
}
