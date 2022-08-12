package kr.inhatc.spring.board.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.entity.Board;
import kr.inhatc.spring.board.entity.Files;


public interface BoardService {

	List<Board> boardList();

	Board boardDetail(int boardIdx);

	void boardUpdate(Board board);

	void boardDelete(int boardIdx);

	Files selectFileInfo(int idx, int boardIdx);

	void saveBoard(Board board,MultipartHttpServletRequest multipartHttpServletRequest, int hitCnt) throws Exception;

	List<Board> searchPosts(String keyword);

	void deleteBoardFile(int idx, int boardIdx);
	
	public Page<Board> getBoardList(Pageable pageable);

	
}