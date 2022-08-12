package kr.inhatc.spring.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.entity.Board;
import kr.inhatc.spring.board.entity.Files;
import kr.inhatc.spring.board.repository.BoardRepository;
import kr.inhatc.spring.utils.FileUtils;


@Service // 서비스를 해주는 파트
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository boardRepository; 

	@Autowired
	private FileUtils fileUtils;

	@Override
	public List<Board> boardList() {
		List<Board> list = boardRepository.findAllByOrderByBoardIdxDesc();
		return list;
	}


	@Override
	public void saveBoard(Board board, MultipartHttpServletRequest multipartHttpServletRequest, int hitCnt) throws Exception {
		board.setCreatorId("admin");
		board.setHitCnt(hitCnt);
		List<Files> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false) {
			board.setFileList(list);
		}
		
		boardRepository.save(board);
	}

	@Override
	public Board boardDetail(int boardIdx) {
		java.util.Optional<Board> optional = boardRepository.findById(boardIdx);
		if (optional.isPresent()) {
			Board board = optional.get();
			return board;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void boardDelete(int boardIdx) {
		boardRepository.deleteById(boardIdx);

	}


	@Override
	public Files selectFileInfo(int idx, int boardIdx) {
		Files boardFile = boardRepository.findBoardFile(idx, boardIdx);
		return boardFile;
	}


	@Override
	public void boardUpdate(Board board) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Board> searchPosts(String keyword) {
		 List<Board> list = boardRepository.findByTitleContaining(keyword);
		return list;
	}


	@Override
	public void deleteBoardFile(int idx, int boardIdx) {
		boardRepository.deleteBoardFile(idx, boardIdx);
		
	}
	
	@Override
	public Page<Board> getBoardList(Pageable pageable) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);

        return boardRepository.findAll(pageable);
	}



//		//1. 파일 저장
//		List<FileDto> list = fileUtils.pareFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
//		if(CollectionUtils.isEmpty(list)==false) {
//			boardMapper.boardFileInsert(list);
//		}
//		
//		//2. 디비 저장
//	}
//
//	@Override
//	public BoardDto boardDeatil(int boardIdx) {
//		BoardDto board = boardMapper.boardDetail(boardIdx);
//		
//		//파일 정보
//		List<FileDto> fileList= boardMapper.selectBoardFileList(boardIdx);
//		board.setFileList(fileList);
//		
//		boardMapper.updateHIt(boardIdx);
//		return board;
//	}

}
