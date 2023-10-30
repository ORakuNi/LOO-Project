package com.example.loo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.loo.model.schedule.Schedule;
import com.example.loo.repository.ScheduleMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService { 
	
	private final ScheduleMapper scheduleMapper;

	public List<Schedule> getPersonalSchedule(String Member_mail){		
		// 개인스케줄 
		List<Schedule> personalSchedule = scheduleMapper.getPersonalSchedule(Member_mail);
		
		return personalSchedule;
	}
	
	public List<Schedule> getCompanySchedule(){
		// 회사스케줄
		List<Schedule> companySchedules = scheduleMapper.getCompanySchedule();	
		
		return companySchedules;
	}
	
	public List<Schedule> getAllSchedules(String Member_mail){			
		// 전체스케줄
		List<Schedule> allSchedules = scheduleMapper.getAllSchedules(Member_mail);
		
		return allSchedules;
	}
	
	public void saveSchedule(Schedule schedule){
		scheduleMapper.saveSchedule(schedule);
	}

	public void deleteSchedule(String schedule_name) {
		scheduleMapper.deleteSchedule(schedule_name);
	}
	
}
