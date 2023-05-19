package com.kh.team4.entity;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.QnaDTO;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Table(name = "files")
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fno;

    @Column
    private String originFile;

    @Column
    private String storedFile;

    // BoardEntity 와 FileEntity 의 관계
    // 게시글과 파일의 관계는 1:N 의 관계, 보드파일 기준으로는 게시글과의 관계는 N:1
    @ManyToOne(fetch = FetchType.LAZY)  // JPA 에서 제공하는 어노테이션 N:1,
    // FetchType: 부모 ENTITY 객체를 조회 했을 때, 자식 ENTITY 객체를 같이 조회 할수 있음
    // LAZY : 필요에 따라 호출해서 사용할 수 있다. 보통 LAZY 를 많이씀
    @JoinColumn(name = "board_bno")
    private Board board;    // 반드시 부모 entity 타입으로 정해줘야됨

    public static Files toFiles(BoardDTO boardDTO) {
        String originFile = boardDTO.getOriginFile().get(0);
        String storedFile = boardDTO.getStoredFile().get(0);

        return Files.builder()
                .originFile(originFile)
                .storedFile(storedFile)
                .build();
    }
}
