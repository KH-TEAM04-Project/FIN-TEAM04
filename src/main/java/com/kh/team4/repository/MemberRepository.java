package com.kh.team4.repository;

import com.kh.team4.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
