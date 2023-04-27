package com.kh.team4.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
@SequenceGenerator(
        name = "member_seq_generator"  //시퀀스 제너레이터 이름
        , sequenceName = "member_seq"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
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

    @Column(columnDefinition = "varchar2(100)", nullable = false)
    private String address;

    @Builder // 생성자 대신 이용하는 친구
    public Member(String mtype, String mname, String regno, String mid, String pwd, String email, String ph, String address) {
        this.mtype = mtype;
        this.mname = mname;
        this.regno = regno;
        this.mid = mid;
        this.pwd = pwd;
        this.email = email;
        this.ph = ph;
        this.address = address;
    }
}
