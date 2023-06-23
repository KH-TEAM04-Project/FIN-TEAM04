package com.kh.team4.service;

import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.dto.ReplyDTO;
import com.kh.team4.entity.Qna;
import com.kh.team4.entity.Reply;
import com.kh.team4.jwt.TokenProvider;
import com.kh.team4.repository.MemberRepository;
import com.kh.team4.repository.QnaRepository;
import com.kh.team4.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyService {

    private final ReplyRepository replyRepository; 
    private final QnaRepository qnaRepository;
    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;


    // 댓글 작성
    @Transactional
    public Long save(ReplyDTO replyDTO, String atk) {
        log.info("reply 서비스 진입");
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        MemberResDTO member = MemberResDTO.of2(memberRepository.findById(mno));

        Long qno = replyDTO.getQno();
        if (qno == null) {
            // qno가 null인 경우 예외 처리 또는 에러 반환
            throw new IllegalArgumentException("게시글 ID(qno)는 null일 수 없습니다.");
            // 또는 적절한 에러 처리를 수행할 수 있습니다.
        }
        Optional<Qna> optionalQna = qnaRepository.findById(qno);
        if (optionalQna.isPresent()) {
            Qna qna = optionalQna.get();
            Reply reply = Reply.toSaveEntity(replyDTO, qna);
            log.info("replyDTO: " + replyDTO);
            return replyRepository.save(reply).getRno();
        } else {
            return null;
        }
    }

    // 댓글 불러오기
    public List<ReplyDTO> findAll(Long qno) {
            log.info("reply findAll 진입");
        // select * from comment_table where board_id=? order by id desc ;
        Qna qna = qnaRepository.findById(qno).get();  // 부모 엔티티를 조회
        List<Reply> replyList = replyRepository.findAllByQnaOrderByRnoDesc(qna);
        log.info("findAllByQnaOrderByRnoDesc" + replyList);
        // 조건에 board_id=? 가 있어서,부모엔티티가 매개변수로 넘어가야됨
        // 호출 결과를 엔티티 리스트로 받아옴

        List<ReplyDTO> replyDTOList = new ArrayList<>();
        for (Reply Reply: replyList) {
            ReplyDTO replyDTO = ReplyDTO.toReplyDTO(Reply);
            log.info("replyDTO : " + replyDTO);
            replyDTOList.add(replyDTO);
        }
        return replyDTOList;
    }
    // 댓글 삭제
    public void delete(Long rno, String atk) {
        System.out.println("댓글 삭제 서비스 진입");
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        MemberResDTO member = MemberResDTO.of2(memberRepository.findById(mno));
        replyRepository.deleteById(rno);
    }


}
