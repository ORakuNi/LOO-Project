package com.example.loo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.loo.model.file.MemberAttachedFile;
import com.example.loo.model.member.Member;

@Mapper
public interface MemberMapper {
	void saveMember(Member member);
	Member findMember(String member_mail);
	void updateMember(Member member);
	void saveFile(MemberAttachedFile attachedFile);
	MemberAttachedFile findFileByMail(String member_mail);
	MemberAttachedFile findFileByAttachedFileId(Long id);
	void removeAttachedFile(Long attachedFile_id);
	}