package com.example.loo.model.member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttachedFile {
	private String member_mail;
	private Long attachedFile_id;
	private String original_filename;
	private String saved_filename;
	private Long file_size;
	
	public AttachedFile(String original_filename, String saved_filename, Long file_size) {
		this.original_filename = original_filename;
		this.saved_filename = saved_filename;
		this.file_size = file_size;
	}
	
	
}
