package com.example.loo.repository;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.loo.model.commute.Commute;


@Mapper
public interface CommuteMapper {

	// 출근하기
	void attendanceCommute(Commute commute);
	// 퇴근하기 
	void leaveCommute(Commute commute);
	// 찾기
	Commute findCommute(Long commute_id);
	// 관리자용 출퇴근 기록 수정
	void updateAdminCommute(Commute commute);
	// 전체 출퇴근 기록
	List<Commute> findAllCommutes(String member_mail); 
}
