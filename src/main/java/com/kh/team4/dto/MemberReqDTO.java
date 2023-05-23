package com.kh.team4.dto;

import com.kh.team4.entity.Authority;
import com.kh.team4.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private String address;
    private String detailaddress;
    private String ph;
    private String mtype;
    private Authority authority;

    @Override
    public String toString(){
        return "mno - " + mno + ", Email - " + email + ", Address - " + address + ", DetailAddress - " + detailaddress + ", ph - " + ph + ", 비번 - " + pwd;

    }

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .mid(mid)
                .pwd(passwordEncoder.encode(pwd))
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(mid, pwd);
    }



}
