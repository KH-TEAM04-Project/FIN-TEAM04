package com.kh.team4.dto;

import com.kh.team4.entity.Member;
import com.kh.team4.entity.Taxrefund;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DcardDTO {
    private Long cno;
    private Member mno; // 회원번호
    private Taxrefund dcard; // 연말정산 체크카드 사용 금액
    private Long airport;
    private Long edu;
    private Long tran;
    private Long cultural;
    private Long delivery;
    private Long stores;
    private Long giftcard;
    private Long food;
    private Long shop;
    private Long post;
    private Long medi;
    private Long cloth;
    private Long commi;
    private Long travel;
    private Long gas;
    private Long coffee;
    private Long commu;
    private Long conveni;
    private Long overseas;
    private Long foreign;
    private Long hotel;
    private Long publicfee;
}
