package com.kh.team4.repository;

import com.kh.team4.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QnaRepository extends JpaRepository<Qna, Long> {
/*    @Modifying  // update, delete 쿼리를 실행해야 할 때는 필수로 붙여야 됨.
    @Query(value = "update Qna b set b.Hits=b.Hits+1 where b.id=:id")
        //JPA 에서 제공하는 어노테이션을 통해서 쿼리를 줄 수 있음.
        //BoardEntity 자리에 원래는 테이블 이름이 와야 하는데, entity 를 기준으로 하기 때문에 entity 이름이 옴.
        //entity 를 기준으로 했을 때는 약어(b)를 쓰는게 필수 BoardEntity = b 로 약칭을 줌
        //(@Param("id") 이 부분과 (15행 마지막) b.id=:id 이 부분이 매칭이 됨.
    void Hits(@Param("id") Long id);*/
@Modifying
@Query(value = "update Qna q set q.hits=q.hits+1 where q.qno=:qno")
void updateHits(@Param("qno") Long qno);
}
