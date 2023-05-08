package com.kh.team4.repository;

import com.kh.team4.entity.Qna;
import com.kh.team4.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

//List<Reply> findAllByQnaOrderByQnoDesc(Qna qna);
    // board_id=? : 보드아이디를 조건으로 조회를 할 때는, 반드시 보드엔티티를 매개변수로 작성해야 됨.

}
