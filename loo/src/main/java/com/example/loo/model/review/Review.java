package com.example.loo.model.review;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Review {
	private Long review_id;
	private Long matgip_num;
	private String member_mail;
	private String member_name;
	private String saved_filename;	
	private String review_contents;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm")
	private LocalDateTime review_create_time;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm")
	private LocalDateTime review_edit_time;
	private int click_like;
}
