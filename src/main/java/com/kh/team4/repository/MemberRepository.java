package com.kh.team4.repository;

import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {
// public interface MemberRepository{

    MemberResDTO findByMidAndPwd(final String mid, final String pwd);


}
