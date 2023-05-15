package com.kh.team4.repository;

import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


    MemberResDTO findByMidAndPwd(final String mid, final String pwd);

    Optional<Member> findByMid(String mid);

    boolean existsByEmail(String email); // 중복가입방지

    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE Member m set (m.password, m.email, m.ph) = (:password, :email, :ph) where m.email = :email", nativeQuery = true)
    // email도 05.12 시점에서는 Unique 값으로 설정되어 있기 때문에, Where절에 사용함.
    void updateMember(@Param("password")String password, @Param("email")String email, @Param("ph")String ph) throws Exception;

}
