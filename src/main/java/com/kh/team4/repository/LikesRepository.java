package com.kh.team4.repository;

import com.kh.team4.entity.Likes;
import com.kh.team4.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    int countByQnaQno(Long qno);

    List<Likes> findAllByQnaQnoAndMemberMno(Long qno, Long mno);

    List<Likes> findByQnaQno(Long qno);
}
