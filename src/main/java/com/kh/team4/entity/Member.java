package com.kh.team4.entity;


import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.dto.QnaDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // (@NoArgsConstructor 이거 쓰면서 @Builder 도 동시에 쓰기 위해 사용)
@Table(name = "members")
@SequenceGenerator(
        name = "member_seq_generator"  //시퀀스 제너레이터 이름
        , sequenceName = "member_seq"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
/*@Embeddable
public class Address {
    @Column(name = "addr1")
    private String address1;
    @Column(name = "addr2")
    private String address2;
    @Column(name = "zipcode")
    private String zipcode;
}*/

@Builder
public class Member {
    @Id // pk: 유저넘버
    @GeneratedValue(  // 기본키를 자동으로 생성해주는 어노테이션
            strategy = GenerationType.SEQUENCE  //사용할 전략을 시퀀스로 선택
            , generator = "member_seq_generator" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정
    )
    private Long mno;

    @Column(name = "mtype", columnDefinition = "VARCHAR2(1) DEFAULT 'U'")
    private String mtype;

    @Column(columnDefinition = "varchar2(20)", nullable = false)
    private String mname;

    @Column(columnDefinition = "varchar2(20)", nullable = false, unique = true) // 주민번호
    private String regno;

    @Column(columnDefinition = "varchar2(20)", nullable = false, unique = true)
    private String mid;

    @Column(columnDefinition = "varchar2(100)", nullable = false)
    private String pwd;

    @Column(columnDefinition = "varchar2(100)", nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "varchar2(20)", nullable = false)
    private String ph;

  /*  @Embedded
    private Address address;*/

 /*   @Builder // 생성자 대신 이용하는 친구 (@NoArgsConstructor 이거 쓰면 @Builder 못쓰는데 @AllArgsConstructor 사용해서 사용가능.)
    public Member(String mtype, String mname, String regno, String mid, String pwd, String email, String ph) {
        this.mtype = mtype;  //
        this.mname = mname; //이름
        this.regno = regno;  //주민번호
        this.mid = mid;  //아이디
        this.pwd = pwd; //비번
        this.email = email; //이메일
        this.ph = ph; //전화번호
        //this.address = address;
    }*/

    public static Member dtoToEntity(MemberReqDTO memberReqDTO) {

        Member member = Member.builder()
                .mid(memberReqDTO.getMid())
                .pwd(memberReqDTO.getPwd())
                .ph(memberReqDTO.getPh())
                .regno(memberReqDTO.getRegno())
                .mname(memberReqDTO.getMname())
                .mtype(memberReqDTO.getMtype())
                .build();
        return member;
    }

}
