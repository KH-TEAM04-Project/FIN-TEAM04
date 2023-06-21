package com.kh.team4;

import com.kh.team4.entity.Authority;
import com.kh.team4.entity.Member;
import com.kh.team4.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.LongStream;

//@SpringBootTest
@RequiredArgsConstructor
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class MemberRepositoryTests {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Test
    void save() {

                LongStream.rangeClosed(1, 50).forEach(i -> {
            try {
                Member parms = Member.builder()
                        .authority(Authority.ROLE_USER)
                        .email(Long.toString(i) + "@example.com")
                        .mid("id" + Long.toString(i))
                        .mname("Mname " + Long.toString(i))
                        .ph("1234")
                        .pwd(passwordEncoder.encode("1392514aA!")) // 비밀번호 인코딩
                        .regno(Long.toString(i) + "1234567890123")
                        .build();

                // 2. 회원 저장
                memberRepository.save(parms);
            } catch (Exception e) {
                System.out.println("Exception occurred at index: " + i);
                e.printStackTrace();
            }
        });

        // 1. 회원 생성
    }
}