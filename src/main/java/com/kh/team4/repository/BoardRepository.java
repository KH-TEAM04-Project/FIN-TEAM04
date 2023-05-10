package com.kh.team4.repository;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.PageResultDTO;
import com.kh.team4.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//Repository인터페이스 : Entity의 기본적인 CRUD 가능하도록 함
// JpaRepository<엔티티명, ID타입> 인터페이스 상속받으면 @Repository 추가X
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Modifying
    @Query(value = "update Board b set b.hits=b.hits+1 where b.bno=:bno")
    void updateHits(@Param("bno") Long bno);

}

