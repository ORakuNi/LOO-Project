package com.example.loo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.loo.model.member.AttachedFile;
import com.example.loo.model.member.Member;
import com.example.loo.model.member.util.FileService;
import com.example.loo.repository.MemberMapper;

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
	public void updateMember(Member givenmember, List<AttachedFile> previousFile, MultipartFile newFile) {
		
		//Member member = memberMapper.findMember(givenmember.getMember_mail());
		log.info("첨부파일:{}", newFile);
//		
//		memberMapper.updateMember(givenmember);

		if(previousFile !=null && newFile != null && newFile.getSize() >0) {
			for(AttachedFile file : previousFile) {
			removeAttachedFile(file.getAttachedFile_id());
		}
		}
		
		
		if(newFile != null && newFile.getSize() >0) {
			//첨부파일을 서버에 저장한다.
			AttachedFile savedFile = fileService.saveFile(newFile, givenmember.getPhone());
			//데이터베이스 저장될 baord_id를 세팅
			savedFile.setMember_mail(givenmember.getMember_mail());
			//첨부파일 내용을 데이터베이스 저장
			memberMapper.saveFile(savedFile);
		}
	}
	
	@Transactional
	public void removeAttachedFile(Long attachedFile_id) {
		AttachedFile attachedFile = memberMapper.findFileByAttachedFileId(attachedFile_id);
		
		if(attachedFile != null) {
			//서버에서 삭제
			String fullPath = uploadPath + "/" + attachedFile.getSaved_filename();
			fileService.deleteFile(fullPath);
			//데이터베이스에서 삭제
			memberMapper.removeAttachedFile(attachedFile.getAttachedFile_id());
		}
	}

}







