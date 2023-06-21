package com.kh.team4.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dcard")
@SequenceGenerator(
        name = "DCARD_SEQ_GENERATOR"  //시퀀스 제너레이터 이름
        , sequenceName = "DCARD_SEQ"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)

public class Dcard {
    @Id // pk 칼럼 지정
    @GeneratedValue(  // 기본키를 자동으로 생성해주는 어노테이션
            strategy = GenerationType.SEQUENCE  //사용할 전략을 시퀀스로 선택
            , generator = "DCARD_SEQ_GENERATOR" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정
    )
    private Long cno;

/*    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_mno")
    private Member mno; // 회원번호*/

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_mno")
    private Taxrefund mno; // 회원번호

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "taxrefend_dard")
    private Taxrefund dcard; // 연말정산 체크카드 사용 금액


    @Column
    private Long airport;

    @Column
    private Long edu;

    @Column
    private Long tran;

    @Column
    private Long cultural;

    @Column
    private Long delivery;

    @Column
    private Long stores;

    @Column
    private Long giftcard;

    @Column
    private Long food;

    @Column
    private Long shop;

    @Column
    private Long post;

    @Column
    private Long medi;

    @Column
    private Long cloth;

    @Column
    private Long commi;

    @Column
    private Long travel;

    @Column
    private Long gas;

    @Column
    private Long coffee;

    @Column
    private Long commu;

    @Column
    private Long conveni;

    @Column
    private Long overseas;

    @Column
    private Long foreign;

    @Column
    private Long hotel;

    @Column
    private Long publicfee;

}
