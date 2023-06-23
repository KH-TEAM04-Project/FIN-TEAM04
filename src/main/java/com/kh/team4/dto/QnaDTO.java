package com.kh.team4.dto;

import com.kh.team4.entity.Member;
import com.kh.team4.entity.Qna;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@ToString   // 필드 값 확인  시 사용
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class QnaDTO {
    private Long qno;
    private String title;
    private String content;
    private String writerID;
    private Integer secret; // Boolean 타입으로 수정 확인해야됨
    private Integer hits;
    private String regDate;
    private String modDate;
    private String memberId; // memberId 필드 추가
    private Long mno;


    public static QnaDTO toQnaDTO(Qna qna) {
        QnaDTO qnaDTO = new QnaDTO();
        qnaDTO.setQno(qna.getQno());
        qnaDTO.setTitle(qna.getTitle());
        qnaDTO.setContent(qna.getContent());
        qnaDTO.setWriterID(qna.getMember().getMid());
        qnaDTO.setSecret(qna.getSecret());
        qnaDTO.setHits(qna.getHits());
        qnaDTO.setRegDate(qna.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        qnaDTO.setMno(qna.getMember().getMno());

        return qnaDTO;
    }
}
