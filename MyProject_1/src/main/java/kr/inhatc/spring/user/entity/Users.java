package kr.inhatc.spring.user.entity;



import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 
import kr.inhatc.spring.board.entity.Files;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
public class Users {
	
	@Id
	@Column(name = "USER_ID")
	private String id;
	private String pw;
	private String name;
	private String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date joinDate;
	private String enabled;
	private String role;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="id")
	//파일관리를 위한 리스트 추가
	private Collection<Files> fileList;
}
