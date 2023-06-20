package com.kh.team4.entity;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.QnaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "files")
@SequenceGenerator(
        name = "files_seq_generator"  //시퀀스 제너레이터 이름
        , sequenceName = "files_seq"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
public class Files  extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "files_seq_generator")
    private Long fno;

    @Column
    private String originalFilename;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "board_bno")
    private Board board;

    public static Files toFiles(Board board, String originalFilename, String storedFileName) {

        Files files = Files.builder()
                .originalFilename(originalFilename)
                .storedFileName(storedFileName)
                .board(board)
                .build();
        return files;
    }

}
