package kr.inhatc.spring.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kr.inhatc.spring.quiz.entity.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {

}