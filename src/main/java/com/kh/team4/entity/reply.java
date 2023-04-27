package com.kh.team4.entity;

import com.kh.team4.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "reply")  // 데이터베이스에 해당하는 테이블
@SequenceGenerator(
        name = "REPLY_SEQ_GENERATOR"  //시퀀스 제너레이터 이름
        , sequenceName = "REPLY_SEQ"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
public class reply {

    @Id // pk 칼럼 지정
    @GeneratedValue(  // 기본키를 자동으로 생성해주는 어노테이션
            strategy = GenerationType.SEQUENCE  //사용할 전략을 시퀀스로 선택
            , generator = "BOARD_SEQ_GENERATOR" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정
    )
    private long rno;

/*    @JoinColumn(name = "members_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Members members;*/

    @JoinColumn(name = "board_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Column(nullable = false)
    private String content;



    @Column
    private String files;
}