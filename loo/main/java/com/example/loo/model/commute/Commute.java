package com.example.loo.model.commute;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class Commute {

	private Long commute_id;
	private String member_mail;
	private LocalDateTime commute_date;
	private LocalDateTime attendance_time;
	private LocalDateTime leave_time;
	private String commute_status;
	private String member_name;
}
