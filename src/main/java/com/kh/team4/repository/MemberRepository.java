package com.kh.team4.repository;

import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
// public interface MemberRepository{

    //  MemberResDTO findByMidAndPwd(final String mid, final String pwd);

    Optional<Member> findByMid(String mid);


}
