package com.kh.team4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto { // 토큰의 값을 헤더에서 뽑거나 삽입할때 사용하기 위해 생성
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long tokenExpiresIn;
}