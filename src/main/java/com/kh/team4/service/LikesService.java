package com.kh.team4.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kh.team4.dto.LikesDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.entity.Likes;
import com.kh.team4.entity.Qna;
import com.kh.team4.jwt.TokenProvider;
import com.kh.team4.repository.LikesRepository;
import com.kh.team4.repository.MemberRepository;
import com.kh.team4.repository.QnaRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Getter
@Setter
public class LikesService {
    private final LikesRepository likesRepository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final QnaRepository qnaRepository;

    // 좋아요 개수 조회 기능
    public int getLikesCount(Long qno, String atk) {
        log.info("좋아요 개수 조회 서비스 진입 qno: " + qno);
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        MemberResDTO member = MemberResDTO.of2(memberRepository.findById(mno));
        return likesRepository.countByQnaQno(qno);
    }

    // 좋아요 기능
    @Transactional
    public void addLike(LikesDTO likesDTO, String atk) {
        log.info("좋아요 서비스 진입 DTO: " + likesDTO);
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        MemberResDTO member = MemberResDTO.of2(memberRepository.findById(mno));

        if (member == null) {
            throw new NotFoundException("Member not found");
        }

        Qna qna = qnaRepository.findById(likesDTO.getQno())
                .orElseThrow(() -> new NotFoundException("Qna not found"));

        Likes likes = new Likes();
        likes.setMember(memberRepository.findById(member.getMno()).orElseThrow(() -> new NotFoundException("Member not found")));
        likes.setQna(qna);
        likesRepository.save(likes);
    }

    // 좋아요 취소 기능
    @Transactional
    public void removeLike(LikesDTO likesDTO, String atk) {
        log.info("좋아요 취소 서비스 진입 DTO: " + likesDTO);
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        MemberResDTO member = MemberResDTO.of2(memberRepository.findById(mno));

        List<Likes> likesList = likesRepository.findAllByQnaQnoAndMemberMno(likesDTO.getQno(), member.getMno());
        if (!likesList.isEmpty()) {
            Likes likes = likesList.get(0); // Assuming you only expect one result
            likesRepository.delete(likes);
        }
    }

    public List<Likes> getMnoList(Long qno) {
        return likesRepository.findByQnaQno(qno);
    }
}
