package com.kh.team4.controller;

import com.kh.team4.dto.ChangePasswordRequestDto;
import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.dto.TokenDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.service.MemberService;
import com.kh.team4.service.RegisterMail;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "localhost:3000")
@RestController
@RequiredArgsConstructor    // 생성자 주입
@RequestMapping()
@ToString
public class MemberController {
    private final MemberService memberService;
    private final JavaMailSender javaMailSender;


    //회원가입 기능 구현
    @PostMapping("/SignUp2")
    public String memberregist(@RequestBody MemberReqDTO memberDTO) {
        System.out.println(memberDTO.toString());
        return memberService.regist(memberDTO);
    }

    // 회원 탈퇴 기능 구현
    @PostMapping("/memberDelete")
    public void memberDelete(@RequestBody MemberReqDTO memberDTO) {
        // mid 가 아닌 mno를 기준으로 삼은 것은 delete메서드가 ID를 기본 골자로 삼고 있기 때문이다.
        Long mno = memberDTO.getMno();
        System.out.println(mno);
        memberService.delete(mno);
    }

    @PostMapping("/memberUpdate")
    public ResponseEntity<MemberResDTO> memberUpdate(@RequestBody MemberReqDTO memberReqDTO) throws Exception {
        System.out.println("받은 값 : " + memberReqDTO.toString());
         return ResponseEntity.ok(memberService.Update(memberReqDTO));
    }


    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO> reissue(@RequestBody TokenDTO tokenReqDTO) {
        return ResponseEntity.ok(memberService.reissue(tokenReqDTO));
    }

    @PostMapping("/sLogin")
    public ResponseEntity<TokenDTO> login(MemberReqDTO requestDto) { // RequestBody사용시 에러뜸.
        System.out.println("컨트롤러에 집입하였습니다. " + requestDto.toString());
        return ResponseEntity.ok(memberService.login(requestDto));
    }

    @Value("${spring.mail.username}")
    private String from;

    @PostMapping("/password")
    public ResponseEntity<MemberResDTO> setMemberPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getEmail(), request.getExPassword(), request.getNewPassword()));
    }

    @Autowired
    RegisterMail registerMail;

    //127.0.0.1:8080/ROOT/api/mail/confirm.json?email
    @PostMapping(value = "/email")
    public String mailConfirm(@RequestParam(name = "email") String email, String mname) throws Exception{
        String code = registerMail.sendSimpleMessage(email);
        System.out.println("사용자에게 발송한 인증코드 ==> " + code);

        return code;
    }

}


