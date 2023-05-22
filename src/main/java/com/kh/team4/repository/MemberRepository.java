package com.kh.team4.repository;

import com.kh.team4.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMid(String mid);

    @Query(value = "select * from members where email = :email and mname = :mname", nativeQuery = true)
    Optional<Member> findByMidwithemailandmname(@Param("email") String email, @Param("mname") String mname);

    Member findByEmail(String email);


    @Query(value = "SELECT mno from members where mid = :mid", nativeQuery = true)
    Long findByMid2(@Param("mid")String mid);

    @Modifying
    @Query("UPDATE Member m SET m.pwd = :password WHERE m.email = :email")
    void updatepwd(@Param("email") String email, @Param("password") String password);

    /*@Modifying // select 문이 아님을 나타낸다
    // @Query(value = "UPDATE Member m set (m.pwd, m.email, m.ph) = (:password, :email, :ph) where m.email = :email", nativeQuery = true)
    @Query(value = "UPDATE Members SET pwd = :password , email = :email , ph = :ph where email = :email", nativeQuery = true)
    // email도 05.12 시점에서는 Unique 값으로 설정되어 있기 때문에, Where절에 사용함.
    void updateMember(@Param("password")String password, @Param("email")String email, @Param("ph")String ph) throws Exception;*/

}
