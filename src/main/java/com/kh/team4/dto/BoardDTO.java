package com.kh.team4.dto;

import lombok.*;
import java.time.LocalDateTime;

// DTO(Data Transfer Object), VO, Bean,
// Entity - 다른 목적을 가진 클래스, 하나의 객체에 담아서 주고 받는 용도

@Getter
@Data
@Builder
@ToString   // 필드 값 확인 시 사용
@NoArgsConstructor  // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardDTO {
    private Long bno;
    private String title;
    private String content;
    private String writerName; //작성자
    private Integer hits;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    }