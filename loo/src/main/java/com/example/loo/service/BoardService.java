package com.example.loo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.loo.model.board.Board;
import com.example.loo.model.file.AttachedFile;
import com.example.loo.model.file.BoardAttachedFile;
import com.example.loo.repository.BoardMapper;
import com.example.loo.util.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
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
        
        // 첨부파일 저장
        if(file != null && file.getSize() > 0) {
        	// 첨부파일을 서버에 저장
        	BoardAttachedFile saveFile = (BoardAttachedFile)fileService.saveFile(file);
        	// 첨부파일 내용을 데이터베이스에 저장
        	saveFile.setBoard_id(board.getBoard_id());
        	boardMapper.saveFile(saveFile);
        }
	}
	
	public List<BoardAttachedFile> findFiles() {
		List<BoardAttachedFile> attachedFiles = boardMapper.findFiles();
		return attachedFiles;
	}


}
