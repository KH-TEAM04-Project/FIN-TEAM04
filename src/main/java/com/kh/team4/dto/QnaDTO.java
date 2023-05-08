package com.kh.team4.dto;

import com.kh.team4.entity.Qna;
import lombok.*;

import java.time.LocalDateTime;

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
    private String writer;
    private Integer secret;
    private Integer hits;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public static QnaDTO toQnaDTO(Qna qna) {
        QnaDTO qnaDTO = new QnaDTO();
        qnaDTO.setTitle(qna.getTitle());
        qnaDTO.setContent(qna.getContent());
        qnaDTO.setSecret(qna.getSecret());
        qnaDTO.setHits(qna.getHits());
        qnaDTO.setRegDate(qna.getRegDate());

        return qnaDTO;
    }


}
