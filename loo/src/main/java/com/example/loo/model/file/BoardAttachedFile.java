package com.example.loo.model.file;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardAttachedFile {
	private Long attachedFile_id;
	private String original_filename;
	private String saved_filename;
	private Long file_size;
	private Long board_id;
	
	public BoardAttachedFile(AttachedFile attachedfile, Long board_id) {
		this.original_filename = attachedfile.getOriginal_filename();
		this.saved_filename = attachedfile.getSaved_filename();
		this.file_size = attachedfile.getFile_size();
		this.board_id = board_id;
	}
}
