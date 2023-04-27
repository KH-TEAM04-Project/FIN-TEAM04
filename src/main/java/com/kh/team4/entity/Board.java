package com.kh.team4.entity;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "board")  // 데이터베이스에 해당하는 테이블
@SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR"  //시퀀스 제너레이터 이름
        , sequenceName = "BOARD_SEQ"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
public class Board {
  @Id // pk 칼럼 지정
  @GeneratedValue(  // 기본키를 자동으로 생성해주는 어노테이션
          strategy = GenerationType.SEQUENCE  //사용할 전략을 시퀀스로 선택
          , generator = "BOARD_SEQ_GENERATOR" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정
  )
  private long bno;

  @Column(length = 50, nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column(length = 1)
  @ColumnDefault("0")
  private Integer secret;

  @Column
  @ColumnDefault("0")
  protected Integer hits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_mno", nullable = false)
    private Member member;

  /* 게시글 수정 */
  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }

/*   댓글 리스트 : 최상위 객체인 게시글이 삭제되면 그 게시글의 댓글 모두 삭제
  여기서 중요한건 mappedBy = "post"를 하지 않으면, 연관관계의 주인이 설정되지 않아 게시글을 삭제할경우 참조키 제약조건 위반으로 예외가 생김*/
/*  @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comment;*/

}
