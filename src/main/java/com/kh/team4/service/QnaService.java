package com.kh.team4.service;

import com.kh.team4.dto.QnaDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.entity.Qna;
import com.kh.team4.repository.MemberRepository;
import com.kh.team4.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;

    private final MemberRepository memberRepository;

    public void save(QnaDTO qnaDTO) {
        Member member = memberRepository.findById(Long.parseLong(qnaDTO.getMember()))
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다."));

        Qna qna = Qna.dtoToEntity(qnaDTO, member);

        qnaRepository.save(qna);

    }
}
