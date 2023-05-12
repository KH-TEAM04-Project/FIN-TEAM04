package com.kh.team4.dto;

import com.kh.team4.entity.Member;
import lombok.*;

import javax.persistence.Id;

@ToString
@Getter
@Setter
@Builder
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

    public MemberResDTO(Member member){
        this.mno = member.getMno();
        this.mname = member.getMname();
        this.regno = member.getRegno();
        this.mid = member.getMid();
        this.pwd = member.getPwd();
        this.email = member.getEmail();
        this.ph = member.getPh();
    }

    // 로그인 하려고 만든거임
    public static MemberResDTO toEntity(Member member) {
        MemberResDTO dto = new MemberResDTO();
        dto.setMid(member.getMid());
        dto.setPwd(member.getPwd());
        return dto;
    }

    public static MemberResDTO of(Member member) {
        return MemberResDTO.builder()
                .mid(member.getMid())
                .mno(member.getMno())
                .mname(member.getMname())
                .regno(member.getRegno())
                .email(member.getEmail())
                .ph(member.getPh())

                .build();
    }
}
