package com.kh.team4.repository;

import com.kh.team4.entity.Cardinfo;
import com.kh.team4.entity.Dcard;
import com.kh.team4.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DcardRepository extends JpaRepository<Dcard, Long> {
    Optional<Dcard> findByMno(Member mno);

}
