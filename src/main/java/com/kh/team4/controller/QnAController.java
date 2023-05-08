package com.kh.team4.controller;

import com.kh.team4.dto.QnaDTO;
import com.kh.team4.entity.Qna;
import com.kh.team4.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
@Log4j2

public class QnAController {
    private final QnaService qnaService;

    @PostMapping("/DoardPage")
    public ResponseEntity<String> register(@RequestBody QnaDTO qnaDTO, Long mno) {
        System.out.println("컨트롤러 진입");
        System.out.println("mno 값" + mno);
        // "/dashboard/write" 주소로 POST 요청이 들어오면 QnaDTO를 받아서 QnaService의 register() 메서드를 호출
        qnaService.register(qnaDTO);
        // register() 메서드에서는 QnaDTO를 이용하여 게시글을 작성하고, Qna 객체를 데이터베이스에 저장.
        return new ResponseEntity<>("게시글이 작성되었습니다.", HttpStatus.OK);
        // 성공적으로 수행되면 "게시글이 작성되었습니다" 메시지와 함께 HttpStatus.OK(200) 상태 코드를 반환.
    }

    @GetMapping("/le")
    public String qnaList() {
        System.out.println("컨트롤러 진입");
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<QnaDTO> qnaDTOList = qnaService.findAll();
        System.out.println("qnaDTOList" + qnaDTOList );
        return "/le";
        // list.html 로 간다.
    }

    @GetMapping("/delete/{qno}")
    public String delete(@PathVariable Long qno) {
        qnaService.delete(qno);
        return "redirect:/qna/";
    }



}
