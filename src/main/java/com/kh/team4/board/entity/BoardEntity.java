package com.kh.team4.board.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "board")  // 데이터베이스에 해당하는 테이블
@SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR"  //시퀀스 제너레이터 이름
        , sequenceName = "BOARD_SEQ"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
public class BoardEntity {
  @Id // pk 칼럼 지정
  @GeneratedValue(  // 기본키를 자동으로 생성해주는 어노테이션
          strategy = GenerationType.SEQUENCE  //사용할 전략을 시퀀스로 선택
          , generator = "BOARD_SEQ_GENERATOR" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정
  )
  private int bno;

  @Column(length = 50, nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column
  @ColumnDefault("0")
  protected int hits;

  @Column
  private String files;

}
