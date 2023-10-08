package com.example.loo.model.commute;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class CommuteAttendance {

	private Long commute_id;
	private String member_mail;
	private LocalDateTime attendance_time;
	private String commute_status;
	
	public static Commute toCommute(CommuteAttendance commuteAttendance) {
		Commute commute = new Commute();
		
		commute.setCommute_id(commuteAttendance.getCommute_id());
		commute.setMember_mail(commuteAttendance.getMember_mail());
		commute.setAttendance_time(commuteAttendance.getAttendance_time());
		commute.setCommute_status(commuteAttendance.getCommute_status());
		
		return commute;
	}
}
