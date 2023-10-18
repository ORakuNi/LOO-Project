package com.example.loo.model.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ScheduleWriteForm {
	
	private String schedule_name;
	private String schedule_contents;
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime schedule_date;
	private String importance;
	private String schedule_type;
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime deadline;
	
	public static Schedule toSchedule(ScheduleWriteForm scheduleWriteForm) {
		Schedule schedule = new Schedule();
		
		schedule.setSchedule_name(scheduleWriteForm.getSchedule_name());
		schedule.setSchedule_contents(scheduleWriteForm.getSchedule_contents());
		schedule.setSchedule_date(scheduleWriteForm.getSchedule_date());
		schedule.setImportance(scheduleWriteForm.getImportance());
		schedule.setSchedule_type(scheduleWriteForm.getSchedule_type());
		schedule.setDeadline(scheduleWriteForm.getDeadline());
		
		return schedule;
		
	}
}
