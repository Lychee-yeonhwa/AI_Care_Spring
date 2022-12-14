package kr.inhatc.spring.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import kr.inhatc.spring.board.dto.BoardDto;
import kr.inhatc.spring.board.dto.FileDto;

@Mapper
public interface BoardMapper {

	//메소드의 이름과 쿼리의 이름을 동일하게 처리
	List<BoardDto> boardList();

	void boardInsert(BoardDto board);

	BoardDto boardDetail(int boardIdx);

	void boardUpdate(BoardDto board);

	void updateHIt(int boardIdx);

	void boardDelete(int boardIdx);

	void boardFileInsert(List<FileDto> list);

	List<FileDto> selectBoardFileList(@Param("boardIdx") int boardIdx);

	FileDto selectFileInfo(@Param("idx") int idx, @Param("boardIdx")int boardIdx);

}
