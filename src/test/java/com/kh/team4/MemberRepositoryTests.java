package com.kh.team4;

import com.kh.team4.entity.Member;
import com.kh.team4.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static com.kh.team4.entity.Authority.ROLE_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    MemberRepository MemberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    void save() {
        // 1. 회원 생성
        IntStream.rangeClosed(1, 50).forEach(i -> {
            try {
                Member member = Member.builder()
                        .authority(ROLE_USER)
                        .email(i + "@example.com")
                        .mid("id" + i)
                        .mname("Mname " + i)
                        .ph("1234")
                        .pwd(passwordEncoder.encode("1392514aA!")) // 비밀번호 인코딩
                        .regno(Integer.toString(i) + "123456-7890123")
                        .build();

                // 2. 회원 저장
                MemberRepository.save(member);
            } catch (Exception e) {
                System.out.println("Exception occurred at index: " + i);
                e.printStackTrace();
            }
        });
    }
}