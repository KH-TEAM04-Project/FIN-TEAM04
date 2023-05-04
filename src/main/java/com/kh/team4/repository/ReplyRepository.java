package com.kh.team4.repository;

import com.kh.team4.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByBoardEntityOrderByIdDesc(Reply boardEntity);

}
