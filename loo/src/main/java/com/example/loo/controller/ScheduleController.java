package com.example.loo.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.example.loo.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("schedule")
public class ScheduleController {

	private final ScheduleService scheduleService;

	@GetMapping("list")
	public String getSchedule(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			Model model) {

		// 개인스케줄 
		List<Schedule> schedule = scheduleService.getPersonalSchedule(loginMember.getMember_mail());
		// 회사스케줄
		List<Schedule> schedules = scheduleService.getCompanySchedule();	
		// 전체스케줄
		List<Schedule> allSchedules = scheduleService.getAllSchedules(loginMember.getMember_mail());
		
		
		
		model.addAttribute("schedule", schedule);
		model.addAttribute("schedules", schedules);
		model.addAttribute("allschedules", allSchedules);
		return "schedule/list";
	}

	@PostMapping("list")
	public String registSchedule(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			HttpServletRequest request, @ModelAttribute("data") ScheduleWriteForm scheduleWriteForm) {

		log.info("가져온거 : {} " , scheduleWriteForm);
		HttpSession session = request.getSession();
		Object attribute = session.getAttribute("loginMember");
		
		Schedule schedule = ScheduleWriteForm.toSchedule(scheduleWriteForm);
		schedule.setMember_mail(((Member) attribute).getMember_mail());
		log.info("가져온거 : {} ", schedule);

		scheduleService.saveSchedule(schedule);
		
		
		return "schedule/list";
	}
	
	@PostMapping("delete")
	public String deleteSchedule(@RequestParam("schedule_name") String schedule_name) {
		
		log.info("지울거 : {}", schedule_name);
		
		scheduleService.deleteSchedule(schedule_name);
		
		return "schedule/list";
	}
	
	

}