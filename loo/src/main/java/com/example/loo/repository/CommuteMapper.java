package com.example.loo.repository;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.loo.model.commute.Commute;


@Mapper
public interface CommuteMapper {

	// 출근하기
	void insertCommute(Commute commute);
	
	// 퇴근하기 
	void updateCommute(Commute commute);
	
	// 전체 출퇴근 기록
	List<Commute> findAllCommutes(String member_mail); 
}
