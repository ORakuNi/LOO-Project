package com.example.loo.model.schedule;


import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Schedule {
	private Long schedule_id;
	private String schedule_name;
	private String schedule_contents;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime schedule_date;
	private String member_mail;
	private String schedule_type;
	private String importance;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime deadline;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime complete_time;
}
