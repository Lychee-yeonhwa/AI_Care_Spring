package kr.inhatc.spring.board.dto;

import java.sql.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BoardDto {
	
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String createId;
	private Date createDate;
	
	//파일관리를 위한 리스트 추가
	private List<FileDto> fileList;
	
	
}
