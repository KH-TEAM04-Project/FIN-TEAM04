package com.kh.team4.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // (@NoArgsConstructor 이거 쓰면서 @Builder 도 동시에 쓰기 위해 사용)
public class MemberResDTO {
    private Long mno;
    private String mtype;
    private String mname;
    private String regno;
    private String mid;
    private String pwd;
    private String email;
    private String ph;
    private String address;
}
