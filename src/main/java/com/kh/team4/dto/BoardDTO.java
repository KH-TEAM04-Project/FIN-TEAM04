package com.kh.team4.dto;

import com.kh.team4.entity.Base;
import com.kh.team4.entity.Board;
import com.kh.team4.entity.Member;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// DTO(Data Transfer Object), VO, Bean,
// Entity - 다른 목적을 가진 클래스, 하나의 객체에 담아서 주고 받는 용도

@Getter
@Data
@Builder
@ToString   // 필드 값 확인 시 사용
@NoArgsConstructor  // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardDTO {
    private Long bno;
    private String title;
    private String content;
    private String writerName; //작성자
    private Integer hits; //조회수
    private String regDate;//작성일
    private String modDate;
   // private boolean isWritten; //작성여부



    public static BoardDTO entityToDTO(Board board) {
            BoardDTO boardDTO = BoardDTO.builder()
                    .bno(board.getBno())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .regDate(board.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .hits(board.getHits())
                    //.isWritten(bool)
                    .build();
            return boardDTO;
        }
}