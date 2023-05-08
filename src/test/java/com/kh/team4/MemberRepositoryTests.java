package com.kh.team4;

import com.kh.team4.entity.Member;
import com.kh.team4.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static com.kh.team4.entity.Authority.ROLE_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    MemberRepository MemberRepository;

    @Test
    void save() {

        // 1. 회원 생성
        Member params = Member.builder()
                .mname("이소연")
                .ph("01012345678")
                .mid("zao")
                .pwd("Cwsook97!")
                .authority(ROLE_USER)
                .email("lee6970808@naver.com")
                .regno("97080821234123")
                .build();


        // 2. 회원저장
        MemberRepository.save(params);

     /*   // 3. 1번 회원 조회
        Member entity = MemberRepository.findById((long) 1).get();
        assertThat(entity.getMname()).isEqualTo("쏘");*/
    }

   /* @Test
    void delete() {

        // 1. 회원조회
        Member entity = MemberRepository.findById((long) 1).get();

        // 2. 회원삭제
        MemberRepository.delete(entity);
    }*/

    /*@Test // Member 객체 100개  생성
    public void insertMembers(){

        IntStream.rangeClosed(1,100).forEach(i -> {

            Member member = Member.builder()
                    .address("경기")
                    .mid("ID" + i)
                    .ph("010-" + i)
                    .mtype("u")
                    .regno("999999-"+i)
                    .email("user" + i + "@aaa.com")
                    .pwd("1111")
                    .mname("USER" + i)
                    .build();

            MemberRepository.save(member);
        });*/


}
