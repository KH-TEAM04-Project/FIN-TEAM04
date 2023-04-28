package com.kh.team4.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString   // 필드 값 확인  시 사용
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class QnADTO {
    private Long qno;
    private String title;
    private String content;
    private Integer secret;
    private Integer hits;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

/*    public static QnADTO toQnADTO(QnAEntity qnaEntiy) {

    }*/

}
