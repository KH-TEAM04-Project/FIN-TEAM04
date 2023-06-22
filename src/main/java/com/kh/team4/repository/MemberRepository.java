package com.kh.team4.repository;

import com.kh.team4.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMid(String mid);

    @Query(value = "select * from members where mno = :mno", nativeQuery = true)
    Optional<Member> findByMemberinfo(@Param("mno") Long mno);

    @Query(value = "select * from members where email = :email and mname = :mname", nativeQuery = true)
    Optional<Member> findByMidwithemailandmname(@Param("email") String email, @Param("mname") String mname);

    Member findByEmail(String email);


    @Query(value = "SELECT mno from members where mid = :mid", nativeQuery = true)
    Long findByMid2(@Param("mid") String mid);

    Optional<Member> findByMno(Long mno);
}
