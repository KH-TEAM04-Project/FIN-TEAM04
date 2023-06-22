package com.kh.team4.repository;

import com.kh.team4.entity.Cardinfo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CardinfoRepository extends JpaRepository<Cardinfo, Long > {

     @Query(value = "SELECT :maxvalueColumnName, :midvalueColumnName, :thrdvalueColumnName FROM cardinfo where cardno = :cardno", nativeQuery = true)
     List recommand2(@Param("maxvalueColumnName") String maxvalueColumnName, @Param("midvalueColumnName") String midvalueColumnName, @Param("thrdvalueColumnName") String thrdvalueColumnName, @Param("cardno") Long cardno);

}
