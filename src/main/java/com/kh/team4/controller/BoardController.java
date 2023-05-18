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

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor    // 생성자 주입
@CrossOrigin(origins = "http://localhost:3000") //CORS 문제 해결 위함
@RequestMapping("/") //board로 변경

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


    @PostMapping("/board/regist") 
    public ResponseEntity<BoardDTO> createArticle(@RequestBody BoardDTO request) {
        return ResponseEntity.ok(service.postBoard(request.getTitle(), request.getContent()));
    }

    //게시글 목록
    @GetMapping("/board/list")
    public List<BoardDTO> boardList() {
        System.out.println("컨트롤러 진입");
        List<BoardDTO> boardDTOList = service.findAll();
        System.out.println("BoardDTOList" + boardDTOList);

        return boardDTOList;
    }

    //게시글 상세조회/수정 불러오기
    @GetMapping({"/board/detail/{bno}", "/board/update/{bno}"})
    public ResponseEntity<BoardDTO> getOneBoard(@PathVariable("bno") Long bno) {
        log.info("상세페이지/수정 컨트롤러");
        // 조회수 하나를 올리고 게시글 데이터 가져와서 나타내야 함
        service.updateHits(bno);
        return ResponseEntity.ok(service.oneBoard(bno));
    }

    //게시글 수정 등록
    @PostMapping("/board/update/{bno}")
    public ResponseEntity<BoardDTO> update(@RequestBody BoardDTO request) {
        return ResponseEntity.ok(service.changeBoard(
                request.getBno(), request.getTitle(), request.getContent()
        ));
    }

    //게시글 삭제
    @DeleteMapping ("/boardDelete/{bno}")
    public ResponseEntity<String> delete(@PathVariable("bno") Long bno) {
        service.deleteBoard(bno);
        return new ResponseEntity<>("회원가입이 완료되었습니다.", HttpStatus.OK);
    }
}