package com.kh.team4.controller;

import com.kh.team4.dto.QnaDTO;
import com.kh.team4.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@CrossOrigin
public class QnAController {
    private final QnaService qnaService;

    @GetMapping("save")
    public List<String> home() {
        return Arrays.asList("글쓰기 페이지입니다.");
    }
    @PostMapping("save")
    public String save(QnaDTO qnaDTO) throws IOException {
        System.out.println("qnaDTO = " + qnaDTO);
        qnaService.save(qnaDTO);
        return "/dashboard/QnA";
    }
}