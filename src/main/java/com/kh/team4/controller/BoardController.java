package com.kh.team4.controller;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.QnaDTO;
import com.kh.team4.entity.Board;
import com.kh.team4.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor    // 생성자 주입
@CrossOrigin(origins = "http://localhost:3000") //CORS 문제 해결 위함
@RequestMapping("/")

public class BoardController {
    private final BoardService service;


    //게시글 등록
    @PostMapping("/CoardPage")
    public ResponseEntity<Long> register(@RequestBody BoardDTO dto) {
        log.info("컨트롤러 진입");
        //새로 추가된 엔티티의 번호
        Long bno = service.register(dto);

        log.info("저장 완료 BNO: " + bno);
        return ResponseEntity.ok(bno);
    }

    @GetMapping("/EoardPage")
    public List<BoardDTO> boardList() {
        System.out.println("컨트롤러 진입");
        List<BoardDTO> boardDTOList = service.findAll();
        System.out.println("BoardDTOList" + boardDTOList);

        return boardDTOList;
    }

    @GetMapping("/boardDelete/{bno}")
    public String delete(@PathVariable("bno") Long bno) {
        System.out.println("삭제 컨트롤러");
        service.delete(bno);
        System.out.println("서비스에서 delete 함수 호출");
        return "/delete";
    }

    @GetMapping({"/BoardReadPage/{bno}", "/EditPage/{bno}"})
    public ResponseEntity<BoardDTO> read(@PathVariable("bno") Long bno) {
        log.info("상세페이지/수정 컨트롤러");
        /* 조회수 하나를 올리고 게시글 데이터 가져와서 나타내야 함*/
        log.info("bno: " + bno);
        service.updateHits(bno);
        BoardDTO boardDTO = service.findById(bno);

        log.info(boardDTO);
        return ResponseEntity.ok(boardDTO);

    }

    @PostMapping("/EditPage/{bno}")
    public ResponseEntity<Long> update(@RequestBody BoardDTO dto) {
        log.info("업데이트 컨트롤러 진입");
        //새로 추가된 엔티티의 번호
        Long bno = service.update(dto);
        log.info("yayayaya y 서버오류?");
        log.info("수정 완료 BNO: " + bno);
        return ResponseEntity.ok(bno);
    }
}