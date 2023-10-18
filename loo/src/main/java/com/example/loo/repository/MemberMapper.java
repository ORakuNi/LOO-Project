package com.example.loo.repository;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.loo.model.member.AttachedFile;
import com.example.loo.model.member.Member;

@Mapper
public interface MemberMapper {
	void saveMember(Member member);
	Member findMember(String member_mail);
	void updateMember(Member member);
	void saveFile(AttachedFile savedFile);
	AttachedFile findFileByAttachedFileId(Long attachedFile_id);
	void removeAttachedFile(Long attachedFile_id);
	
	}