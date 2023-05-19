package com.kh.team4.entity;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.QnaDTO;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString(exclude = "member") //fetch 방식이 Lazy일 경우 사용
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


    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer hits;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_mno")
    private Member member;

    // 첨부파일 관련
    @Column
    private Integer fileAttached; //파일 첨부 여부 (첨부 1, 미첨부 0)

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Files> FilesList = new ArrayList<>();
    // 게시글 하나에 보드파일이 여러개가 올 수 있도록 참조 관계 설정


    public static Board dtoToEntity(BoardDTO dto, Member member) {
        //Member member = Member.builder().mid(dto.getWriterID()).build();
        Board board = Board.builder()
//                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .hits(dto.getHits())
                .member(member)
                .build();
        return board;
    }
/*    public static Board createBoard(Long bno, String title, String content, Member member) {
        Board board = new Board();
        board.bno = bno;
        board.title = title;
        board.content = content;
        board.member = member;
        board.hits = 0;

        //board.fileAttached = 0; // 0 = 파일 없음
        return board;
    }*/
    /* 게시글 수정 */
    public static Board changeBoard(Board board, String title, String content) {
        board.title = title;
        board.content = content;
        return board;
    }
}
