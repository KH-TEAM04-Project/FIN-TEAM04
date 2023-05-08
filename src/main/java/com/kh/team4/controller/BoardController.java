package com.kh.team4.controller;

import com.kh.team4.dto.BoardDTO;
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

    /*@GetMapping("/boardRead/{bno}")
    public String update(@PathVariable Long mno) {
    BoardDTO boardDTO = service.findById(mno);
    return boardDTO;
    }
    @PostMapping("/boardUpdate/{bno}")
    public String updateForm(@PathVariable BoardDTO boardDTO) {
        //BoardDTO board = service.modify(boardDTO);
        return board.toString();
        return "redirect:/boardRead/" + boardDTO.getBno();
    }*/

/*    @GetMapping("/board")
    public String boardList() {
        log.info("컨트롤러 진입");
    List<BoardDTO> boardDTOList = service.findAll();
        return null ;
    }*/


}