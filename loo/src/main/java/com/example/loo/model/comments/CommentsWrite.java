package com.example.loo.model.comments;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentsWrite {
	@NotBlank(message = "내용을 입력해주세요.")
	@Size(max = 100, message = "100자 이내로 입력해 주세요.")
	private String comment_contents;
	
	public static Comments toComments(CommentsWrite commentsWrite) {
		Comments comments = new Comments();
		comments.setComment_contents(commentsWrite.getComment_contents());
		
		return comments;
	}
}
