package com.kh.team4.repository;

import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
// public interface MemberRepository{

    //  MemberResDTO findByMidAndPwd(final String mid, final String pwd);

    Optional<Member> findByMid(String mid);


    boolean existsByMid(String mid); // 중복가입방지


}
