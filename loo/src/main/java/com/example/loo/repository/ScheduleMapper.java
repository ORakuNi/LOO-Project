package com.example.loo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.loo.model.schedule.Schedule;

@Mapper
public interface ScheduleMapper {
	
	// 개인 스케줄 가져오기(해당 사원)
	List<Schedule> getPerSchedule(String member_mail);
	
	// 전체 회사 스케줄 가져오기
	List<Schedule> getAllSchedule();
	
	// 전체 스케줄 가져오기
	List<Schedule> allSchedules(String member_mail);

	
	void saveSchedule(Schedule schedule);

	void deleteSchedule(String schedule_name);




}
