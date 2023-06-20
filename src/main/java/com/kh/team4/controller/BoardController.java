package com.kh.team4.controller;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.QnaDTO;
import com.kh.team4.entity.Board;
import com.kh.team4.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor    // 생성자 주입
@CrossOrigin(origins = "http://localhost:3000") //CORS 문제 해결 위함
@RequestMapping("/board") //board로 변경

public class BoardController {
    private final BoardService service;
/*    @GetMapping("/EoardPage") //list로 변경
    public ResponseEntity<Page<PageResponseDto>> pageArticle(@RequestParam(name = "page") int page) {
        return ResponseEntity.ok(service.pageArticle(page));
    }*/

/*    @PostMapping("/CoardPage") //register로 변경
    public ResponseEntity<Long> createArticle(@RequestBody BoardDTO dto) {
        log.info("등록 컨트롤러");
        log.info("writer :" + dto.getWriterID());
        Long bno = service.postBoard(dto);
        log.info("BNO: " + bno);
        return ResponseEntity.ok(bno);

    }*/


    @PostMapping("/regist")
    public ResponseEntity<BoardDTO> createArticle(@ModelAttribute BoardDTO boardDTO, MultipartHttpServletRequest request) throws IOException {
        log.info("게시글 작성 컨트롤러 진입");
        log.info("boardDTO 값 : " + boardDTO);

        List<MultipartFile> files = request.getFiles("boardfiles");
        if (files != null && !files.isEmpty()) {
            boardDTO.setBoardFiles(files);
        }
        return ResponseEntity.ok(service.postBoard(boardDTO));
    }



/*    @PostMapping("/Notice")
    public void saveNotice(@ModelAttribute NoticeRequestDto noticeRequestDto, @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            noticeRequestDto.setAttach(file);
        }
        noticeService.saveNotice(noticeRequestDto);

    }*/

    //게시글 목록
    @GetMapping("/list")
    public List<BoardDTO> boardList() {
        System.out.println("컨트롤러 진입");
        List<BoardDTO> boardDTOList = service.findAll();
        System.out.println("BoardDTOList" + boardDTOList);

        return boardDTOList;
    }

    //게시글 상세조회/수정 불러오기
    @GetMapping({"/detail/{bno}", "/update/{bno}"})
    public ResponseEntity<BoardDTO> getOneBoard(@PathVariable("bno") Long bno) {
        log.info("상세페이지/수정 컨트롤러");
        // 조회수 하나를 올리고 게시글 데이터 가져와서 나타내야 함
        service.updateHits(bno);
        return ResponseEntity.ok(service.findById(bno));
    }

    //게시글 수정 등록
    @PostMapping("/update/{bno}")
    public ResponseEntity<Long> update(@PathVariable Long bno, @RequestBody BoardDTO dto) {
        log.info("업데이트 컨트롤러 진입");
        //새로 추가된 엔티티의 번호
        dto.setBno(bno);
        log.info("수정 dto: " + dto);
        Long updateBno = service.modify(dto);
        log.info("수정 완료 BNO: " + updateBno);
        return ResponseEntity.ok(updateBno);
    }

    //게시글 삭제
    @GetMapping("/boardDelete/{bno}")
    public String delete(@PathVariable("bno") Long bno) {
        System.out.println("삭제 컨트롤러 진입");
        service.delete(bno);
        System.out.println("서비스에서 delete 함수 호출");
        return "/delete";
    }
}