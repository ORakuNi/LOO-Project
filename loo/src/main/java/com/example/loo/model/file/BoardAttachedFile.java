package com.example.loo.model.file;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardAttachedFile extends AttachedFile {
	private Long board_id;
}
