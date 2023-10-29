package com.example.loo.model.board;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Comments {
	private Long comment_id;
	private Long board_id;
	private String member_mail;
	private String member_name;
	private String saved_filename;
	private String comment_contents;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime comment_create_time;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime comment_edit_time;
	private int click_like;
}