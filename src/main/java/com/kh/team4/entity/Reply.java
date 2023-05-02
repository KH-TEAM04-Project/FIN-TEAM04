package com.kh.team4.entity;

import lombok.Getter;
import javax.persistence.*;

@Entity
@Getter
@Table(name = "reply")  // 데이터베이스에 해당하는 테이블
@SequenceGenerator(
        name = "REPLY_SEQ_GENERATOR"  //시퀀스 제너레이터 이름
        , sequenceName = "REPLY_SEQ"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
public class Reply extends Base {

    @Id // pk 칼럼 지정
    @GeneratedValue(  // 기본키를 자동으로 생성해주는 어노테이션
            strategy = GenerationType.SEQUENCE  //사용할 전략을 시퀀스로 선택
            , generator = "REPLY_SEQ_GENERATOR" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정
    )
    private long rno;

    @JoinColumn(name = "member_mno", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Member member;

    @JoinColumn(name = "qna_qno", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Qna qna;

    @Column(nullable = false)
    private String content;

}