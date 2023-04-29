package com.kh.team4;

import com.kh.team4.entity.Member;
import com.kh.team4.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    MemberRepository MemberRepository;

    @Test
    void save() {

        // 1. 회원 생성
        Member params = Member.builder()
                .mname("쏘")
                .ph("01041756934")
                .mid("za5")
                .pwd("za5")
                .email("lee970808@naver.com")
                .regno("970808213456")
                .address("경기도 용인시")
                .build();


        // 2. 회원저장
        MemberRepository.save(params);

        // 3. 1번 회원 조회
        Member entity = MemberRepository.findById((long) 1).get();
        assertThat(entity.getMname()).isEqualTo("쏘");
    }

   /* @Test
    void delete() {

        // 1. 회원조회
        Member entity = MemberRepository.findById((long) 1).get();

        // 2. 회원삭제
        MemberRepository.delete(entity);
    }*/

}
