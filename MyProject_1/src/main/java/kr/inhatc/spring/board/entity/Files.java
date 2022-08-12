package kr.inhatc.spring.board.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_jpa_file")
@NoArgsConstructor
@Data
public class Files {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idx;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;
	private String creatorId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date createDatetime;
	
}
