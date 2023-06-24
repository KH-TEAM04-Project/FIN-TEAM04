package com.kh.team4.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardinfoRepository {
    @Query(value = "SELECT :maxvalueColumnName FROM Cardinfo cardinfo WHERE cardinfo.cardno = :cardno and :maxvalueColumnName is not null")
    String top1(@Param("maxvalueColumnName") String maxvalueColumnName, @Param("cardno") Long cardno);

    @Query(value = "SELECT :midvalueColumnName FROM Cardinfo cardinfo WHERE cardinfo.cardno = :cardno and :midvalueColumnName is not null")
    String top2(@Param("midvalueColumnName") String midvalueColumnName, @Param("cardno") Long cardno);

    @Query(value = "SELECT :thrdvalueColumnName FROM Cardinfo cardinfo WHERE cardinfo.cardno = :cardno and :thrdvalueColumnName is not null")
    String top3(@Param("thrdvalueColumnName") String thrdvalueColumnName, @Param("cardno") Long cardno);

    @Query(value = "SELECT cardinfo FROM Cardinfo cardinfo WHERE cardinfo.cardno = :cardno")
    String findByCno(@Param("cardno") Long cardno);
}
