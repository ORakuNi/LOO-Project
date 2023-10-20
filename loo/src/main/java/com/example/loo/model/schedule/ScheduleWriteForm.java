package com.example.loo.model.schedule;

import java.util.Date;



import lombok.Data;

@Data
public class ScheduleWriteForm {
	
	private String schedule_name;
	private String schedule_contents;
	private Date schedule_date;
	private String importance;
	private String schedule_type;
	private Date deadline;
	
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
