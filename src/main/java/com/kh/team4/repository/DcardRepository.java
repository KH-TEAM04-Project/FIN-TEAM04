package com.kh.team4.repository;

import com.kh.team4.entity.Dcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DcardRepository extends JpaRepository<Dcard, Long> {
    @Query("SELECT c.airport, c.cloth, c.coffee, c.commi, c.commu, c.conveni, c.cultural, c.delivery, c.edu, c.food, c.foreign, c.gas, c.giftcard, c.hotel, c.medi, c.overseas, c.post, c.publicfee, c.shop, c.stores, c.tran, c.travel " +
            "FROM Dcard c " +
            "ORDER BY c.airport DESC, c.cloth DESC, c.coffee DESC, c.commi DESC, c.commu DESC, c.conveni DESC, c.cultural DESC, c.delivery DESC, c.edu DESC, c.food DESC, c.foreign DESC, c.gas DESC, c.giftcard DESC, c.hotel DESC, c.medi DESC, c.overseas DESC, c.post DESC, c.publicfee DESC, c.shop DESC, c.stores DESC, c.tran DESC, c.travel DESC")
    List<Object[]> findTop3Columns();
}
