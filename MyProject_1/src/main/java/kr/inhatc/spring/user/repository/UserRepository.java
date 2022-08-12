package kr.inhatc.spring.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.board.entity.Files;
import kr.inhatc.spring.user.entity.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, String>{

	List<Users> findAllByOrderByIdDesc();

	@Query("SELECT file FROM Files file WHERE id = :id AND idx = :idx")
	Files findUserFile(@Param("idx")int idx,@Param("id") String id);

}
