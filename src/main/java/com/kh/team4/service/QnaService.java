package com.kh.team4.service;

import com.kh.team4.dto.QnaDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.entity.Qna;
import com.kh.team4.repository.MemberRepository;
import com.kh.team4.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Log4j2
public class QnaService {
    private final QnaRepository qnaRepository;

    private final MemberRepository memberRepository;

    public void register(QnaDTO qnaDTO, Long mno) {
        Member writer = memberRepository.findById(mno)
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다." + mno));
        Qna qna = Qna.dtoToEntity(qnaDTO, writer);
        qnaRepository.save(qna);

        log.info("Q&A 등록: {}", qna);

    }
}
