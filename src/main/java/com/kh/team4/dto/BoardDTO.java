package com.kh.team4.dto;

import com.kh.team4.entity.Board;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private String writerID; //작성자
    private Integer hits; //조회수
    private String regDate;//작성일
    private String modDate;
    private Long mno;

    // 첨부파일 관련
    private String files;

    private List<MultipartFile> boardFiles;  // 여러개의 파일을 담아주려면 List 적용
    // MultipartFile : 스프링에서 제공하는 인터페이스 - 실제 파일을 담아줄 수 있는 역할을 함.
    private List<String> originFile;    // 원본 파일 이름
    private List<String> storedFile;    // 서버 저장용 파일 이름
    private int fileAttached;   // 파일 첨부 여부(첨부 1, 미첨부 0)

    public static BoardDTO entityToDTO(Board board) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writerID(board.getMember().getMid())
                .regDate(board.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .hits(board.getHits())
                .mno(board.getMember().getMno())
                .build();
/*// 파일첨부
        if(board.getFileAttached() == 0) {
            boardDTO.setFileAttached(board.getFileAttached());    // 0
        } else {
            List<String> originFileList = new ArrayList<>();
            List<String> storedFileList = new ArrayList<>();
            boardDTO.setFileAttached(board.getFileAttached());    // 1

            for (Files files: board.getFilesList()) {
                originFileList.add(files.getOriginalFilename());
                storedFileList.add(files.getStoredFileName());
            }
            boardDTO.setOriginFile(originFileList);
            boardDTO.setStoredFile(storedFileList);
        }*/
        return boardDTO;

    }


    public static BoardDTO of(Board board, boolean bool) {
        return BoardDTO.builder()
                .bno(board.getBno())
                .writerID(board.getMember().getMid())
                .title(board.getTitle())
                .content(board.getContent())
                .hits(board.getHits())
                .regDate(board.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }
}