package com.kh.team4.entity;

import com.kh.team4.dto.BoardDTO;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@DynamicInsert //Insert시 Null인 필드를 제외하기위해 사용
@DynamicUpdate
@ToString(exclude = "member") //fetch 방식이 Lazy일 경우 사용
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
    private Long bno;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;


    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer hits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_mno")
    private Member member;

    @Column(columnDefinition = "integer default 0")
    private Integer fileAttached; //파일 첨부 여부 (첨부 1, 미첨부 0)

  /*  @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Files> FilesList = new ArrayList<>();
    // 게시글 하나에 보드파일이 여러개가 올 수 있도록 참조 관계 설정*/

    public static Board dtoToEntity(BoardDTO dto) {
        Member member = Member.builder().mno(dto.getMno()).build();
        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .hits(dto.getHits())
                .member(member)
                .fileAttached(0)
                .build();
        return board;
    }

    /* 게시글 수정 */
    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}