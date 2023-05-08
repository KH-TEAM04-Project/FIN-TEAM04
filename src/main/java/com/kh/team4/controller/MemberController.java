package com.kh.team4.controller;

import com.kh.team4.dto.ChangePasswordRequestDto;
import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "localhost:3000")
@RestController
@RequiredArgsConstructor    // 생성자 주입
@RequestMapping()
@ToString
public class MemberController {
    private final MemberService memberService;

   /* @PostMapping("/sLogin")
    public String login(MemberReqDTO memberDTO) {
        MemberResDTO login = memberService.login(memberDTO);
        if (login != null) {
            String answer = "Y";
            return answer;
        } else {
            // login 실패
            return "/login";
        }
    }*/

}


