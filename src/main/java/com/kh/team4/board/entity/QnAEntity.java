package com.kh.team4.board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "qna")
@SequenceGenerator(
        name = "QNA_SEQ_GENERATOR"  //시퀀스 제너레이터 이름
        , sequenceName = "QNA_SEQ"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
public class QnAEntity {
    @Id // pk 칼럼 지정
    @GeneratedValue(  // 기본키를 자동으로 생성해주는 어노테이션
            strategy = GenerationType.SEQUENCE  //사용할 전략을 시퀀스로 선택
            , generator = "QNA_SEQ_GENERATOR" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정
    )
    private int qno;

    @Column(length = 50, nullable = false)
    private String title;

    @Column
    private String content;

    @Column
    private String files;

    @Column
    private String secret;

    @Column
    private int hits;

}