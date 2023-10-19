package com.example.loo.model.file;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MemberAttachedFile extends AttachedFile {
	private String member_mail;
}
