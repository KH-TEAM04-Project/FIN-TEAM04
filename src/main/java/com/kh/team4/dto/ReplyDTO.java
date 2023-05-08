package com.kh.team4.dto;

import com.kh.team4.entity.Reply;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ReplyDTO {
    private Long rno;
    private String content;
    private Long qnaQno;
    private LocalDateTime regDate;

    // entity 를 dto 로 변환하는 내용
    public static ReplyDTO toReplyDTO(Reply reply, Long qnaQno) {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setRno(reply.getRno());
        replyDTO.setContent(reply.getContent());
        replyDTO.setRegDate(reply.getRegDate());
        replyDTO.setQnaQno(qnaQno);
        return replyDTO;
    }
}
