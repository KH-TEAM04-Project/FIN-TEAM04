package com.kh.team4.service;

import com.kh.team4.dto.ReplyDTO;
import com.kh.team4.entity.Qna;
import com.kh.team4.entity.Reply;
import com.kh.team4.repository.QnaRepository;
import com.kh.team4.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    private final QnaRepository qnaRepository;

    public Long save(ReplyDTO replyDTO) {
        Optional<Qna> optionalQna = qnaRepository.findById(replyDTO.getQnaQno());
        if (optionalQna.isPresent()) {
            Qna qna = optionalQna.get();
            Reply reply = Reply.dtoToEntity(replyDTO, qna);
            // builder 라는 것을 쓰기도 함
            return replyRepository.save(reply).getRno();

        } else {
            return null;
        }

    }

}
