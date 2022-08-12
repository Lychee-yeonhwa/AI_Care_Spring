package kr.inhatc.spring.board.dto;

import javax.persistence.Id;

import lombok.Data;

@Data
public class FileDto {
	
	@Id
	private int idx;
	private int boardIdx;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;
	
	
}
