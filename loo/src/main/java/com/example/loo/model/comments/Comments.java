package com.example.loo.model.comments;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Comments {
	private Long comment_id;
	private Long board_id;
	private String member_mail;
	private String member_name;
	private String comment_contents;
	private LocalDateTime comment_create_time;
	private LocalDateTime comment_edit_time;
}
