package com.kh.team4.repository;

import com.kh.team4.entity.Taxrefund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CheckCardRepository extends JpaRepository<Taxrefund, Long> {
    /*@Query("SELECT c.column1, c.column2, c.column3 " +
            "FROM Taxrefund c " +
            "ORDER BY c.column1 DESC, c.column2 DESC, c.column3 DESC")
    List<Object[]> findTop3Columns();*/
}
