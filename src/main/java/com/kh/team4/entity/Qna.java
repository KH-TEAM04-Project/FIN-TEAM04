package com.kh.team4.entity;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.QnaDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert //Insert시 Null인 필드를 제외하기위해 사용
@DynamicUpdate  //Update시 Null인 필드를 제외하기위해 사용


@Table(name = "qna")
@SequenceGenerator(
        name = "QNA_SEQ_GENERATOR"  //시퀀스 제너레이터 이름
        , sequenceName = "QNA_SEQ"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
public class Qna extends Base {
    @Id // pk 칼럼 지정
    @GeneratedValue(  // 기본키를 자동으로 생성해주는 어노테이션
            strategy = GenerationType.SEQUENCE  //사용할 전략을 시퀀스로 선택
            , generator = "QNA_SEQ_GENERATOR" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정
    )
    private Long qno;

    @Column(length = 50)
    private String title;

    @Column
    private String content;

    @Column(length = 1)
    @ColumnDefault("0")
    private Integer secret;

    @Column
    @ColumnDefault("0")
    private Integer hits;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "member_mno", nullable = false)
    @Column
    private String writer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "qna", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    public static Qna dtoToEntity(QnaDTO qnaDTO) {

//        Member member = Member.builder().mno(writer.getMno()).build();
        Qna qna = Qna.builder()
            .qno(qnaDTO.getQno())
            .title(qnaDTO.getTitle())
            .content(qnaDTO.getContent())
            .secret(qnaDTO.getSecret())
            .hits(qnaDTO.getHits())
            .writer(qnaDTO.getWriter())
            .build();
        return qna;
    }

    public static Qna toUpdateEntity(QnaDTO qnaDTO) {
        Qna qna = Qna.builder()
                .qno(qnaDTO.getQno())
                .title(qnaDTO.getTitle())
                .content(qnaDTO.getContent())
                .build();
        System.out.println("보드 dto -> 엔티티 변환 :" + qna);
        return qna;
    }
}