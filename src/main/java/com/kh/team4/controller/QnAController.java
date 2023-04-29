package com.kh.team4.controller;

import com.kh.team4.dto.QnADTO;
import com.kh.team4.service.QnAService;
import com.kh.team4.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class QnAController {
    private final QnAService qnaService;

    @GetMapping("save")
    public List<String> home() {
        return Arrays.asList("글쓰기 페이지입니다.");
    }
    @PostMapping("save")
    public String save(QnADTO qnaDTO) throws IOException {
        System.out.println("qnaDTO = " + qnaDTO);
        qnaService.save(qnaDTO);
        return "/dashboard/QnA";
    }
}
