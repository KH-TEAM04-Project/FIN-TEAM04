package com.kh.team4.controller;

import com.kh.team4.dto.QnaDTO;
import com.kh.team4.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:3000")

public class QnAController {
    private final QnaService qnaService;

    @PostMapping("/qna")
    public ResponseEntity<String> register(@RequestBody QnaDTO qnaDTO, @RequestParam Long mno) {
        // "/dashboard/write" 주소로 POST 요청이 들어오면 QnaDTO를 받아서 QnaService의 register() 메서드를 호출
        qnaService.register(qnaDTO, mno);
        // register() 메서드에서는 QnaDTO를 이용하여 게시글을 작성하고, Qna 객체를 데이터베이스에 저장.
        return new ResponseEntity<>("게시글이 작성되었습니다.", HttpStatus.OK);
        // 성공적으로 수행되면 "게시글이 작성되었습니다" 메시지와 함께 HttpStatus.OK(200) 상태 코드를 반환.
    }
    @GetMapping("/qna")
    public List<String> register() {
        return Arrays.asList("qna 페이지입니다");
    }
}
