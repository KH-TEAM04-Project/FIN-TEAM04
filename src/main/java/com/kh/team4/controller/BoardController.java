package com.kh.team4.controller;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor    // 생성자 주입
@RequestMapping("/dashboard")

public class BoardController {
 /*   private final BoardService boardService;

    @GetMapping("/register")
    public void register() {
        log.info("Register get..");
    }

    @PostMapping("/register")
    public String register(BoardDTO dto) {
        log.info("dto.." + dto);
        return "/dashboard/register";
    }
*/

}
