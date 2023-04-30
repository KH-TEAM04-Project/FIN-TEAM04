package com.kh.team4.controller;

import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor    // 생성자 주입
@RequestMapping()
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public MemberResDTO login(@RequestBody final MemberResDTO params) {
        MemberResDTO entity = memberService.findBy(params);
        return entity;
    }
}


