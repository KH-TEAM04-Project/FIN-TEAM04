package com.kh.team4;

import com.kh.team4.dto.QnaDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.entity.Qna;
import com.kh.team4.repository.MemberRepository;
import com.kh.team4.repository.QnaRepository;
import com.kh.team4.service.QnaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;



import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class QnaServiceTest {

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private QnaService qnaService;

    @Test
    void save() {
        // given
        Member member = Member.builder()
                .mname("길동")
                .regno("123456780")
                .mid("hongGilDon")
                .pwd("1234")
                .email("honggil@naver.com")
                .ph("01012345678")
                .build();

        member = memberRepository.save(member);

        QnaDTO qnaDTO = new QnaDTO();
        qnaDTO.setTitle("문의합니다.");
        qnaDTO.setContent("어떤 상품을 구매해야 할까요?");
        qnaDTO.setMember(String.valueOf(member));

        // when
        qnaService.save(qnaDTO);

        // then
        Qna savedQna = qnaRepository.findAll().get(0);
        assertEquals(qnaDTO.getTitle(), savedQna.getTitle());
        assertEquals(qnaDTO.getContent(), savedQna.getContent());
        assertEquals(member.getMno(), savedQna.getMember().getMno());
    }
}
