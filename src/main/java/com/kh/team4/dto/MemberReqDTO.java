package com.kh.team4.dto;

import com.kh.team4.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // (@NoArgsConstructor 이거 쓰면서 @Builder 도 동시에 쓰기 위해 사용)
public class MemberReqDTO {
    private Long mno;
    private String mname;
    private String mid;
    private String regno;
    private String email;
    private String pwd;
    private String detailaddress;
    private String address;
    private String ph;
    private String mtype;

    @Override
    public String toString(){
        return mid + pwd;

    }



}
