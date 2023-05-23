package com.kh.team4.dto;

import com.kh.team4.entity.Reply;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자

public class ReplyDTO {
    private Long rno;
    private String content;
    private Long qnaQno;
    private LocalDateTime regDate;

    // entity 를 dto 로 변환하는 내용
    public static ReplyDTO toReplyDTO(Reply Reply, Long QnaQno) {
        ReplyDTO ReplyDTO = new ReplyDTO();
        ReplyDTO.setRno(Reply.getRno());
        ReplyDTO.setContent(Reply.getContent());
        ReplyDTO.setQnaQno(QnaQno);
        ReplyDTO.setRegDate(Reply.getRegDate());

        return ReplyDTO;
    }

}
