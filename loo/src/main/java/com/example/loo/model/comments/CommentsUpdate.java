package com.example.loo.model.comments;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CommentsUpdate {
	@NotBlank(message = "내용을 입력해주세요.")
	private String comment_contents;
}
