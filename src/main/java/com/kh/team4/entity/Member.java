package com.kh.team4.entity;


import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.embeded.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor // (@NoArgsConstructor 이거 쓰면서 @Builder 도 동시에 쓰기 위해 사용)
@Table(name = "members")
@SequenceGenerator(
        name = "member_seq_generator"  //시퀀스 제너레이터 이름
        , sequenceName = "member_seq"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
@Builder
public class Member {
    @Id // pk: 유저넘버
    @GeneratedValue(  // 기본키를 자동으로 생성해주는 어노테이션
            strategy = GenerationType.SEQUENCE  //사용할 전략을 시퀀스로 선택
            , generator = "member_seq_generator" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정
    )
    private Long mno;

    @Column(columnDefinition = "varchar2(20)")
    private String mname;

    @Column(columnDefinition = "varchar2(20)", unique = true) // 주민번호
    private String regno;

    @Column(columnDefinition = "varchar2(20)", nullable = false, unique = true)
    private String mid;

    @Column(columnDefinition = "varchar2(100)", nullable = false)
    private String pwd;

    @Column(columnDefinition = "varchar2(100)", unique = true)
    private String email;

    @Column(columnDefinition = "varchar2(20)")
    private String ph;

    @Column
    private String ProfilePhoto;


    @Enumerated(EnumType.STRING)
    private Authority authority;

    // qna 관계매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Qna> qnaList = new ArrayList<>();

    // reply 관계매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    // taxrefund 관계매핑
    @OneToMany(mappedBy = "mno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Taxrefund> taxrefundList = new ArrayList<>();

    // board 관계매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boardList = new ArrayList<>();

    @Embedded
    private Address address;

    public void setPwd(String pwd) { this.pwd = pwd; }

    public Member(Long mno) {
        this.mno = mno;
    }


    public static Member dtoToEntity(MemberReqDTO memberReqDTO) {

        Member member = Member.builder()
                .email(memberReqDTO.getEmail())
                .mid(memberReqDTO.getMid())
                .pwd(memberReqDTO.getPwd())
                .ph(memberReqDTO.getPh())
                .regno(memberReqDTO.getRegno())
                .mname(memberReqDTO.getMname())
                .authority(memberReqDTO.getAuthority())
                .build();
        return member;
    }
public  static Member findMid(MemberResDTO memberResDTO){
        Member member = Member.builder()
                .mid(memberResDTO.getMid())
                .build();
        return member;
}

    public static Member dtoToEntity2(MemberReqDTO memberReqDTO, PasswordEncoder passwordEncoder) {

        Member member = Member.builder()
                .email(memberReqDTO.getEmail())
                .mid(memberReqDTO.getMid())
                .ph(memberReqDTO.getPh())
                .regno(memberReqDTO.getRegno())
                .mname(memberReqDTO.getMname())
                .pwd(passwordEncoder.encode(memberReqDTO.getPwd()))
                .authority(Authority.ROLE_USER)
                .address(new Address(memberReqDTO.getAddress(), memberReqDTO.getDetailAddress()))
                .build();
        return member;
    }

    public Member(MemberResDTO memberReqDTO) {

        Member member = Member.builder()
                .email(memberReqDTO.getEmail())
                .mid(memberReqDTO.getMid())
                .ph(memberReqDTO.getPh())
                .regno(memberReqDTO.getRegno())
                .mname(memberReqDTO.getMname())
                .pwd(memberReqDTO.getPwd())
                .authority(Authority.ROLE_USER)
                .build();
    }

    public void toUpdate(String email, String ph, String address, String detailAddress) {
        this.email = email;
        this.ph = ph;
        this.address = new Address(address, detailAddress);
    }

    public String confirm(){
        return "Mno : " + this.mno + ",  Email : " + this.email + ",  Ph : " + this.ph;
    }

    public String setProfilePhoto(String uploadImageUrl) {
        return uploadImageUrl;
    }
}
