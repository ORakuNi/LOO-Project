package com.example.loo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.loo.model.schedule.Schedule;

@Mapper
public interface ScheduleMapper {
	
	// 개인 스케줄 가져오기(해당 사원)
	List<Schedule> getPersonalSchedule(String member_mail);
	
	// 전체 회사 스케줄 가져오기
	List<Schedule> getCompanySchedule();
	
	// 전체 스케줄 가져오기
	List<Schedule> getAllSchedules(String member_mail);

	void saveSchedule(Schedule schedule);

	void deleteSchedule(String schedule_name);




}
