package com.kh.team4.entity;

import com.kh.team4.dto.QnaDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Builder
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
    private long qno;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(length = 1)
    @ColumnDefault("0")
    private Integer secret;

    @Column
    @ColumnDefault("0")
    private Integer hits;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_mno", nullable = false)
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "qna", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    public static Qna dtoToEntity(QnaDTO qnaDTO) {

        Qna qna = Qna.builder()
            .title(qnaDTO.getTitle())
            .content(qnaDTO.getContent())
            .secret(qnaDTO.getSecret())
            .hits(qnaDTO.getHits())
            .build();
        return qna;
    }
}