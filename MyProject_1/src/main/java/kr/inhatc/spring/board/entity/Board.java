package kr.inhatc.spring.board.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(   
        name="BOARD_SEQ_GEN", //시퀀스 제너레이터 이름
        sequenceName="BOARD_SEQ", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
        )
@Table(name = "t_jpa_board")
@NoArgsConstructor
@Data
public class Board {
	
	@Id
	@GeneratedValue(
            strategy=GenerationType.SEQUENCE, // 사용할 전략을 시퀀스로  선택
            generator="BOARD_SEQ_GEN" //식별자 생성기를 설정해놓은  BOARD_SEQ_GEN으로 설정        
            )
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date createDate;


	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="boardIdx")
	
	//파일관리를 위한 리스트 추가
	private Collection<Files> fileList;
}
