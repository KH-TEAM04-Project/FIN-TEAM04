package com.kh.team4.repository;

import com.kh.team4.entity.Member;
import com.kh.team4.entity.Taxrefund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaxrefundRepository extends JpaRepository<Taxrefund, Long> {
    Optional<Taxrefund> findByMno(Member mno);

}
