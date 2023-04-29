package com.kh.team4.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "files")
@SequenceGenerator(
        name = "FILES_SEQ_GENERATOR"  //시퀀스 제너레이터 이름
        , sequenceName = "FILES_SEQ"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
public class Files {
    @Id
    @GeneratedValue(  // 기본키를 자동으로 생성해주는 어노테이션
            strategy = GenerationType.SEQUENCE  //사용할 전략을 시퀀스로 선택
            , generator = "FILES_SEQ_GENERATOR" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정
    )
    private Long fno;

    @Column
    private String originfile;

    @Column
    private String storedfile;

    // BoardEntity 와 FileEntity 의 관계
    // 게시글과 파일의 관계는 1:N 의 관계, 보드파일 기준으로는 게시글과의 관계는 N:1
    @ManyToOne(fetch = FetchType.LAZY)  // JPA 에서 제공하는 어노테이션 N:1,
    // FetchType: 부모 ENTITY 객체를 조회 했을 때, 자식 ENTITY 객체를 같이 조회 할수 있음
    // LAZY : 필요에 따라 호출해서 사용할 수 있다. 보통 LAZY 를 많이씀
    @JoinColumn(name = "board_bno")
    private Board board;    // 반드시 부모 entity 타입으로 정해줘야됨


}
