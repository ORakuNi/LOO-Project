package com.example.loo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.loo.model.board.AttachedFile;
import com.example.loo.model.board.Board;
import com.example.loo.repository.BoardMapper;
import com.example.loo.util.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

	private final BoardMapper boardMapper;
	private final FileService fileService;
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Transactional
	public void saveBoard(Board board, MultipartFile file) {
        // 데이터베이스에 저장한다.
        boardMapper.saveBoard(board);
        
        //첨부파일 저장
        if(file != null && file.getSize() > 0) {
        	AttachedFile saveFile = fileService.saveFile(file);        	
        
        //첨부파일 내용을 데이터베이스에 저장
        saveFile.setBoard_id(board.getBoard_id());
        boardMapper.saveFile(saveFile);
        }
	}
	
	@Transactional
	public void updateBoard(Board updateBoard, boolean isFileRemoved, MultipartFile file) {
		Board board = boardMapper.findBoard(updateBoard.getBoard_id());
		if (board != null) {
			boardMapper.updateBoard(updateBoard);
			// 첨부파일 점보를 가져온다.
			AttachedFile attachedFile = boardMapper.findFileByBoardId(updateBoard.getBoard_id());
			if (attachedFile != null && (isFileRemoved || (file != null && file.getSize() > 0))) {
				removeAttachedFile(attachedFile.getAttachedFile_id());
			}
			
			
			if (file != null && file.getSize() > 0) {
				
				AttachedFile savedFile = fileService.saveFile(file);
				savedFile.setBoard_id(updateBoard.getBoard_id());
				boardMapper.saveFile(savedFile);
			}
		}
	}
	
	@Transactional
	public void removeAttachedFile(Long attachedFile_id) {
		AttachedFile attachedFile = boardMapper.findFileByAttachedFileId(attachedFile_id);
		if (attachedFile != null) {
			String fullPath = uploadPath + "/" + attachedFile.getSaved_filename();
			fileService.deleteFile(fullPath);
			log.info("기존 파일 삭제 : {}", attachedFile);
			boardMapper.removeAttachedFile(attachedFile.getAttachedFile_id());
		}
	}
	
	public void removeBoard(Long board_id) {
		AttachedFile attachedFile = findFileByBoardId(board_id);
		if (attachedFile != null) {
			removeAttachedFile(attachedFile.getAttachedFile_id());
		}
		boardMapper.removeBoard(board_id);
	}
	
	public AttachedFile findFileByBoardId(Long board_id) {
		return boardMapper.findFileByBoardId(board_id);
	}
	
	
	public AttachedFile findFileByAttachedFileId(Long attachedFile_id) {
		return boardMapper.findFileByAttachedFileId(attachedFile_id);
	}
	
}