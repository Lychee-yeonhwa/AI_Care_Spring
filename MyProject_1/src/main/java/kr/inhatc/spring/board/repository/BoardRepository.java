package kr.inhatc.spring.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.inhatc.spring.board.entity.Board;
import kr.inhatc.spring.board.entity.Files;

@Repository
public interface BoardRepository extends CrudRepository<Board, Integer>{
	
	List<Board> findAllByOrderByBoardIdxDesc();
	
	List<Board> findByTitleContaining(String keyword);
	
	@Query("SELECT file FROM Files file WHERE board_idx = :boardIdx AND idx = :idx")
	Files findBoardFile(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
    
	@Modifying
	@Transactional
	@Query("DELETE FROM Files file WHERE board_idx = :boardIdx AND idx = :idx")
	void deleteBoardFile(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
	
	Page<Board> findAll(Pageable pageable);


}

