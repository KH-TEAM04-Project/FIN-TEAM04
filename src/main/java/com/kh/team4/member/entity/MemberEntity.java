package com.kh.team4.member.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    private Long memberId;

    @Column
    private String memberEmail;

    @Column
    private String memberName;

    @Column
    private String memberPassword;

    @Column
    private int memberAge;

    @Column
    private int memberPhone;

    @Column
    private String memberGender;

    @Column
    private String memberAddress;


}
