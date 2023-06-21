package com.kh.team4.repository;

import com.kh.team4.entity.Dcard;
import com.kh.team4.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DcardRepository extends JpaRepository<Dcard, Long> {
/*    @Query(value = "SELECT c.airport, c.cloth, c.coffee, c.commi, c.commu, c.c
onveni, c.cultural, c.delivery, c.edu, c.food, c.foreign, c.gas, c.giftcard, c.hotel, c.medi, c.overseas, c.post, c.publicfee, c.shop, c.stores, c.tran, c.travel " +
            "FROM Dcard c " +
            "WHERE member_mno = ?1 " +
            "ORDER BY c.airport DESC, c.cloth DESC, c.coffee DESC, c.commi DESC, c.commu DESC, c.conveni DESC, c.cultural DESC, c.delivery DESC, c.edu DESC, c.food DESC, c.foreign DESC, c.gas DESC, c.giftcard DESC, c.hotel DESC, c.medi DESC, c.overseas DESC, c.post DESC, c.publicfee DESC, c.shop DESC, c.stores DESC, c.tran DESC, c.travel DESC", nativeQuery = true)
    Optional<Dcard> findTop3Columns(Long mno);*/


    Optional<Dcard> findByMno(Member mno);


}
