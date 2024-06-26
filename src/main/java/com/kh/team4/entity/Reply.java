package com.kh.team4.entity;

import com.kh.team4.dto.ReplyDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Table(name = "reply")  // 데이터베이스에 해당하는 테이블
@NoArgsConstructor
@AllArgsConstructor
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

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qno")
    private Qna qna;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_mno")
    private Member member;

    public static Reply toSaveEntity(ReplyDTO replyDTO, Qna qna) {
        Member member = Member.builder().mno(replyDTO.getMno()).build();

        Reply reply = Reply.builder()
                .content(replyDTO.getContent())
                .qna(qna)
                .member(member)
                .build();

        return reply;

    }
}