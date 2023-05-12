package com.kh.team4.dto;

import com.kh.team4.entity.Board;
import lombok.*;


import java.time.format.DateTimeFormatter;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponseDTO {
    private Long boardBno;
    private String boardTitle;
    private String memberName;
    private String regDate;

    public static PageResponseDTO of(Board board) {
        return PageResponseDTO.builder()
                .boardBno(board.getBno())
                .boardTitle(board.getTitle())
                .memberName(board.getMember().getMname())
                .regDate(board.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }
}
