package com.kh.team4.controller;

import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "localhost:3000")
@RestController
@RequiredArgsConstructor    // 생성자 주입
@RequestMapping()
@ToString
public class MemberController {
    private final MemberService memberService;
    // private final JavaMailSender javaMailSender;


    //회원가입 기능 구현
    @PostMapping("/SignUp2")
    public String memberregist(@RequestBody MemberReqDTO memberDTO) {
        System.out.println(memberDTO.toString());
        return memberService.regist(memberDTO);
    }

}


