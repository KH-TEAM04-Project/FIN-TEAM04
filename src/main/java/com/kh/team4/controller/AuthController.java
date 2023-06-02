package com.kh.team4.controller;

import com.kh.team4.dto.MailDTO;
import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.TokenDTO;
import com.kh.team4.service.MemberService;
import com.kh.team4.service.SendEmailService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "localhost:3000")
@RestController
@RequiredArgsConstructor    // 생성자 주입
@RequestMapping("/auth")
@ToString
public class AuthController {
    private final MemberService memberService;
    private final JavaMailSender javaMailSender;
    private final SendEmailService sendEmailService;

    @PostMapping("/sLogin")
    public ResponseEntity<TokenDTO> login(MemberReqDTO requestDto) { // RequestBody사용시 에러뜸.
        System.out.println("컨트롤러에 집입하였습니다. " + requestDto.toString());
        return ResponseEntity.ok(memberService.login(requestDto));
    }

    @PostMapping("/SignUp2")
    public String memberregist(@RequestBody MemberReqDTO memberDTO) {
        System.out.println(memberDTO.toString());
        return memberService.regist(memberDTO);
    }

    @PostMapping("/check/findID")
    public String ID_find(@RequestBody MemberReqDTO memreq) {
        String email = memreq.getEmail();
        String mname = memreq.getMname();
        System.out.println(mname + email);
        return memberService.findID2(email, mname);
    }

    @GetMapping("/check/findPw")
    public @ResponseBody Map<String, Boolean> pw_find(String email, String mname) {
        Map<String, Boolean> json = new HashMap<>();
        boolean pwFindCheck = memberService.memberEmailCheck(email, mname);

        System.out.println(pwFindCheck);
        json.put("check", pwFindCheck);
        return json;
    }

    @PostMapping("/check/findPw/sendEmail")
    public @ResponseBody void sendEmail(MemberReqDTO requestDto) {
        String email = requestDto.getEmail();
        String mname = requestDto.getMname();
        System.out.println(requestDto.getEmail() + requestDto.getPwd());
        MailDTO dto = sendEmailService.createMailAndChangePassword(email, mname);
        System.out.println(dto.toString());
        sendEmailService.mailSend(dto);
    }
}
