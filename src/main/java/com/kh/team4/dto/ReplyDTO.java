package com.kh.team4.dto;

import com.kh.team4.entity.Reply;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자

public class ReplyDTO {
    private Long rno;
    private String content;
    private Long qno;

    private String regDate; // 추가: 문자열 형식의 작성일 필드
    private LocalDateTime rawRegDate; // 변경: 기존 LocalDateTime 필드 이름 변경

    // entity 를 dto 로 변환하는 내용
    public static ReplyDTO toReplyDTO(Reply reply) {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setRno(reply.getRno());
        replyDTO.setContent(reply.getContent());
        replyDTO.setQno(reply.getQna().getQno());
        replyDTO.setRawRegDate(reply.getRegDate()); // 수정: 이름 변경된 필드에 값 설정

        // regDate 필드에 형식화된 작성일 설정
        replyDTO.setRegDate(reply.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return replyDTO;
    }

}
