package kr.inhatc.spring.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kr.inhatc.spring.quiz.entity.Result;

@Repository
public interface ResultRepo extends JpaRepository<Result, Integer> {
	
}
