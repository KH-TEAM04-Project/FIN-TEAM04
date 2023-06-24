package com.kh.team4.entity;


import com.kh.team4.embeded.Cardbene;
import lombok.*;

import javax.persistence.*;

@ToString
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Cardinfo")
@SequenceGenerator(
        name = "cardinfo_seq_generator"  //시퀀스 제너레이터 이름
        , sequenceName = "cardinfo_seq"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
@Builder
public class Cardinfo {
    @Id // pk: 시퀀스
    @GeneratedValue(  // 기본키를 자동으로 생성해주는 어노테이션
            strategy = GenerationType.SEQUENCE  //사용할 전략을 시퀀스로 선택
            , generator = "cardinfo_seq_generator" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정
    )
    private Long cardno;

    @Column(columnDefinition = "varchar2(200)") // 카드이름
    private String cname;

    @Column(columnDefinition = "varchar2(200)") // 은행이름
    private String bname;

    @Column(columnDefinition = "varchar2(200)")
    private String imgadd;

    @Column
    private String airport;

    @Column
    private String edu;

    @Column
    private String tran;

    @Column
    private String cultural;

    @Column
    private String delivery;

    @Column
    private String stores;

    @Column
    private String giftcard;

    @Column
    private String food;

    @Column
    private String shop;

    @Column
    private String post;

    @Column
    private String medi;

    @Column
    private String cloth;

    @Column
    private String commi;

    @Column
    private String travel;

    @Column
    private String gas;

    @Column
    private String coffee;

    @Column
    private String commu;

    @Column
    private String conveni;

    @Column
    private String overseas;

    @Column
    private String foreign;

    @Column
    private String hotel;

    @Column
    private String publicfee;

    @Column
    private String allfor;

/*    @Embedded
    private Cardbene cardbenefit;*/

}
