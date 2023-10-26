package com.example.loo.service;


import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.loo.model.file.AttachedFile;
import com.example.loo.model.file.MemberAttachedFile;
import com.example.loo.model.member.Member;
import com.example.loo.repository.MemberMapper;
import com.example.loo.util.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberService {
	
	private final MemberMapper memberMapper;
	private final FileService fileService;
	@Value("${file.upload.path}")
    private String uploadPath;
	  
	@Transactional
	public void updateMember(Member loginMember, Member givenmember, MemberAttachedFile previousFile, MultipartFile newFile) {
		
		log.info("첨부파일:{}", newFile.getSize());

		if(previousFile !=null && newFile != null && newFile.getSize() >0) {
			removeAttachedFile(previousFile.getAttachedFile_id());
		}
		
		
		if(newFile != null && newFile.getSize() >0) {
			//첨부파일을 서버에 저장한다.
			AttachedFile attachedFile = fileService.saveFile(newFile);
			MemberAttachedFile savedFile = new MemberAttachedFile(attachedFile, givenmember.getMember_mail());

			givenmember.setSaved_filename(savedFile.getSaved_filename());
			loginMember.setSaved_filename(savedFile.getSaved_filename());

			//첨부파일 내용을 데이터베이스 저장
			memberMapper.saveFile(savedFile);
			memberMapper.updateMember(givenmember);
		}
	}
	
	@Transactional
	public void removeAttachedFile(Long attachedFile_id) {
		MemberAttachedFile attachedFile = memberMapper.findFileByAttachedFileId(attachedFile_id);
		
		if(attachedFile != null) {
			//서버에서 삭제
			String fullPath = uploadPath + "/" + attachedFile.getSaved_filename();
			fileService.deleteFile(fullPath);
			//데이터베이스에서 삭제
			memberMapper.removeAttachedFile(attachedFile.getAttachedFile_id());
		}
	}

}






