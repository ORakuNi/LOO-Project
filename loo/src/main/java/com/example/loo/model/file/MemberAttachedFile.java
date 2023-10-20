package com.example.loo.model.file;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MemberAttachedFile {
	private Long attachedFile_id;
	private String original_filename;
	private String saved_filename;
	private Long file_size;
	private String member_mail;
	
	public MemberAttachedFile(AttachedFile attachedfile, String member_mail) {
		this.original_filename = attachedfile.getOriginal_filename();
		this.saved_filename = attachedfile.getSaved_filename();
		this.file_size = attachedfile.getFile_size();
		this.member_mail = member_mail;
	}
}
