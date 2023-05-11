package com.kh.team4.controller;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

                log.info("BNO: " + bno);
        return ResponseEntity.ok(bno);
////        return new ResponseEntity<>("공지사항이 등록되었습니다.", HttpStatus.OK);
    }

/*    //게시글 삭제
    @DeleteMapping("/register/{bno}") ///register/{id}
    public ResponseEntity<String> delete(@PathVariable Integer bno) {
        log.info(bno + "delete");
        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }*/

}