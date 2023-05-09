package com.kh.team4.entity;

import com.kh.team4.dto.BoardDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString(exclude = "writer") //fetch 방식이 Lazy일 경우 사용
//Eager Loading(즉시로딩):특정 엔티티를 조회할 때 연관관계를 가진 모든 엔티티를 같이 로딩 -> 성능 저하
// LAZY : 지연로딩,즉시로딩과 반대, 필요할 때만 사용, LAZY 사용하면 @ToString(exclude) 무조건 사용
// @ToString(): 해당 클래스의 모든 멤버 변수를 출력
//Board 객체의 @ToString()을 하면 writer 변수로 선언된 Member 객체도 함께 출력해야 하며
// Member 객체를 출력하기 위해서는 Member 객체의 @ToString()이 호출되어야 하고 이때 DB 연결이 필요
// -> exclude 지정

@Table(name = "board")  // 데이터베이스에 해당하는 테이블
@SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR"  //시퀀스 제너레이터 이름
        , sequenceName = "BOARD_SEQ"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
public class Board extends Base {
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


    @Column
    @ColumnDefault("0")
    protected Integer hits;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_mno")
    private Member writer;

/*    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "writer", referencedColumnName = "mid")
    private Member writer;*/

    /* 게시글 수정 */
    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

    /*   댓글 리스트 : 최상위 객체인 게시글이 삭제되면 그 게시글의 댓글 모두 삭제
      여기서 중요한건 mappedBy = "post"를 하지 않으면, 연관관계의 주인이 설정되지 않아 게시글을 삭제할경우 참조키 제약조건 위반으로 예외가 생김*/
/*
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<board> comment;
*/

    public static Board dtoToEntity(BoardDTO dto) {
        System.out.println("dtoToEntity 실행");

        //작성자
        //Member member = Member.builder().mname(dto.getWriterName()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
               // .writer(member)
                .build();
      //  System.out.println("member :" + member);
        System.out.println("보드 dto -> 엔티티 변환 :" + board);
        return board;

    }


}
