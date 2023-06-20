package com.kh.team4.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString   // 필드 값 확인  시 사용
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자

public class LikesDTO {
    private Long mno;
    private Long qno;
}
