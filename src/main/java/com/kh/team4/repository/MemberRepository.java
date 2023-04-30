package com.kh.team4.repository;

import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {
    MemberResDTO findByMidAndPwd(final String mid, final String Pwd);
}
