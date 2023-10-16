package com.example.loo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.loo.model.member.Member;
import com.example.loo.model.schedule.Schedule;
import com.example.loo.model.schedule.ScheduleWriteForm;
import com.example.loo.repository.ScheduleMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("schedule")
public class ScheduleController {

	@Autowired
	private final ScheduleMapper scheduleMapper;

	@GetMapping("list")
	public String getSchedule(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			Model model) {

		// 개인스케줄 
		List<Schedule> schedule = scheduleMapper.getPerSchedule(loginMember.getMember_mail());
		// 회사스케줄
		List<Schedule> schedules = scheduleMapper.getAllSchedule();	
		// 전체스케줄
		List<Schedule> allSchedules = scheduleMapper.allSchedules(loginMember.getMember_mail());
		
		
		// log.info("schedule : {}", schedule);
		// log.info("schedules : {}", schedules);
		model.addAttribute("schedule", schedule);
		model.addAttribute("schedules", schedules);
		model.addAttribute("allschedules", allSchedules);
		return "schedule/list";
	}

	@PostMapping("list")
	public String registSchedule(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			HttpServletRequest request, @ModelAttribute("data") ScheduleWriteForm scheduleWriteForm) {

//		log.info("가져온거 : {} " , scheduleWriteForm);
		HttpSession session = request.getSession();
		Object attribute = session.getAttribute("loginMember");

		Schedule schedule = ScheduleWriteForm.toSchedule(scheduleWriteForm);
		schedule.setMember_mail(((Member) attribute).getMember_mail());
		log.info("가져온거 : {} ", schedule);

		scheduleMapper.saveSchedule(schedule);
		
		
		return "schedule/list";
	}
	
	@PostMapping("delete")
	public String deleteSchedule(@RequestParam("schedule_name") String schedule_name) {
		
		log.info("지울거 : {}", schedule_name);
		
		scheduleMapper.deleteSchedule(schedule_name);
		
		return "schedule/list";
	}
	
	

}
