package com.kh.team4.dto;

import com.kh.team4.entity.Base;
import com.kh.team4.entity.Board;
import com.kh.team4.entity.Files;
import com.kh.team4.entity.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// DTO(Data Transfer Object), VO, Bean,
// Entity - 다른 목적을 가진 클래스, 하나의 객체에 담아서 주고 받는 용도

@Getter
@Setter
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
    private boolean isWritten; //작성여부

    // 첨부파일 관련
    private List<MultipartFile> boardFile;  // 여러개의 파일을 담아주려면 List 적용
    // MultipartFile : 스프링에서 제공하는 인터페이스 - 실제 파일을 담아줄 수 있는 역할을 함.
    private List<String> originFile;    // 원본 파일 이름
    private List<String> storedFile;    // 서버 저장용 파일 이름
    /* 오늘 프로필 사진을 올리면, 내일 또 올릴 수도 있음. 파일이름이 같은 경우 오늘인지 내일인지 구분 할 수 없음
    사용자가 파일을 올렸을 때, 저장용 이름으로 저장해서 파일의 중복을 방지 */
    private int fileAttached;   // 파일 첨부 여부(첨부 1, 미첨부 0)

    public static BoardDTO entityToDTO(Board board) {
            BoardDTO boardDTO = BoardDTO.builder()
                    .bno(board.getBno())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .regDate(board.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .hits(board.getHits())
                    //.isWritten(bool)
                    .build();
        // 파일첨부
        if(board.getFileAttached() == 0) {
            boardDTO.setFileAttached(board.getFileAttached());    // 0
            // 보드 DTO 에 FileAttached 의 값을 엔티티에 담겨있는 어테치드의 값으로 세팅
        } else {
            List<String> originFileList = new ArrayList<>();
            List<String> storedFileList = new ArrayList<>();
            boardDTO.setFileAttached(board.getFileAttached());    // 1
            // 추가로 파일이름을 가져가야 함. 그래야 저장경로에 있는 파일을 화면에 보여줄 수 있음.
            // originalFileName, storedFileName 은 boar_file_table(BoardFileEntity) 에 들어있음

            for (Files files: board.getFilesList()) {

//                boardDTO.setOriginFile(board.getFilesList().get(0).getOriginFile());
//                boardDTO.setStoredFile(board.getFilesList().get(0).getStoredFile());

                originFileList.add(files.getOriginFile());
                storedFileList.add(files.getStoredFile());
            }
            boardDTO.setOriginFile(originFileList);
            boardDTO.setStoredFile(storedFileList);
        }

            return boardDTO;
        }
/*    public static PageResponseDto  of(Board board) {
        return PageResponseDto.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .writerID(board.getMember().getMid())
                .regDate(board.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }*/

    public static BoardDTO of(Board board, boolean bool) {
        return BoardDTO.builder()
                .bno(board.getBno())
                .writerID(board.getMember().getMid())
                .title(board.getTitle())
                .content(board.getContent())
                .hits(board.getHits())
                .regDate(board.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .isWritten(bool)
                .build();
    }
}