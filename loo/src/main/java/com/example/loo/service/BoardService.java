package com.example.loo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.loo.model.board.Board;
import com.example.loo.model.board.BoardCategory;

import com.example.loo.model.file.AttachedFile;
import com.example.loo.model.file.BoardAttachedFile;
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
        	AttachedFile attachedFile = fileService.saveFile(file);
			BoardAttachedFile saveFile = new BoardAttachedFile(attachedFile, board.getBoard_id());
        
			//첨부파일 내용을 데이터베이스에 저장
			boardMapper.saveFile(saveFile);
        }
	}
	
	@Transactional
	public void updateBoard(Board updateBoard, boolean isFileRemoved, MultipartFile file) {
		Board board = boardMapper.findBoard(updateBoard.getBoard_id());
		if (board != null) {
			boardMapper.updateBoard(updateBoard);
			// 첨부파일 점보를 가져온다.
			BoardAttachedFile boardAttachedFile = boardMapper.findFileByBoardId(updateBoard.getBoard_id());
			if (boardAttachedFile != null && (isFileRemoved || (file != null && file.getSize() > 0))) {
				removeAttachedFile(boardAttachedFile.getAttachedFile_id());
			}
			
			
			if (file != null && file.getSize() > 0) {
				
				AttachedFile attachedFile = fileService.saveFile(file);
				BoardAttachedFile savedFile = new BoardAttachedFile(attachedFile, updateBoard.getBoard_id());
				boardMapper.saveFile(savedFile);
			}
		}
	}
	
	@Transactional
	public void removeAttachedFile(Long attachedFile_id) {
		BoardAttachedFile attachedFile = boardMapper.findFileByAttachedFileId(attachedFile_id);
		if (attachedFile != null) {
			String fullPath = uploadPath + "/" + attachedFile.getSaved_filename();
			fileService.deleteFile(fullPath);
			log.info("기존 파일 삭제 : {}", attachedFile);
			boardMapper.removeAttachedFile(attachedFile.getAttachedFile_id());
		}
	}
	
	public void removeBoard(Long board_id) {
		BoardAttachedFile attachedFile = findFileByBoardId(board_id);
		if (attachedFile != null) {
			removeAttachedFile(attachedFile.getAttachedFile_id());
		}
		boardMapper.removeBoard(board_id);
	}
	
	public BoardAttachedFile findFileByBoardId(Long board_id) {
		return boardMapper.findFileByBoardId(board_id);
	}
	
	
	public BoardAttachedFile findFileByAttachedFileId(Long attachedFile_id) {
		return boardMapper.findFileByAttachedFileId(attachedFile_id);
	}
	
	public int getTotal(BoardCategory board_category) {
		return boardMapper.getTotal(board_category);
	}

	public List<Board> findAllClubs() {
		return boardMapper.findAllClubs();
	}

	public Board findBoard(Long board_id) {
		return boardMapper.findBoard(board_id);
	}
	
	public Board readBoard(Long board_id) {
		Board board = findBoard(board_id);
		// 조회수 1 증가
        boardMapper.addHit(board_id);
        // 조회수를 증가하여 데이터베이스에 업데이트 한다.
        updateBoard(board, false, null);
		return board;
	}

	public List<Board> findAllBoards(BoardCategory board_category) {
		return boardMapper.findAllBoards(board_category);

	}
	
	public List<Board> searchBoards(BoardCategory board_category, String searchText){
		if(searchText.equals("")) {
			  List<Board> boards = boardMapper.findAllBoards(board_category);
			  return boards;
		} else 
			 return boardMapper.findBoards(searchText, board_category);
		
	}
	
}