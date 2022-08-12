package kr.inhatc.spring.quiz.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Component
@Entity
@SequenceGenerator(   
        name="RESULTS_SEQ_GEN", //시퀀스 제너레이터 이름
        sequenceName="RESULTS_SEQ", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
        )	
@Table(name = "results")
public class Result {

	@Id
	@GeneratedValue(
	            strategy=GenerationType.SEQUENCE, //사용할 전략을 시퀀스로  선택
	            generator="RESULTS_SEQ_GEN" //식별자 생성기를 설정해놓은  RESULTS_SEQ_GEN으로 설정        
	            )
	private int id;
	private String username;
	private int totalCorrect = 0;

	public Result() {
		super();
	}

	public Result(int id, String username, int totalCorrect) {
		super();
		this.id = id;
		this.username = username;
		this.totalCorrect = totalCorrect;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getTotalCorrect() {
		return totalCorrect;
	}

	public void setTotalCorrect(int totalCorrect) {
		this.totalCorrect = totalCorrect;
	}

	@Override
	public String toString() {
		return "resultSET[id=" + id + ", username=" + username + ", totalCorrect=" + totalCorrect + ", ]";
	}
}
