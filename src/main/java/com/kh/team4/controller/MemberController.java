package com.kh.team4.controller;

import com.kh.team4.dto.*;
import com.kh.team4.entity.Member;
import com.kh.team4.service.CustomUserDetailsService;
import com.kh.team4.service.MemberService;
import com.kh.team4.service.SendEmailService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "localhost:3000")
@RestController
@RequiredArgsConstructor    // 생성자 주입
@RequestMapping()
@ToString
public class MemberController {
    private final MemberService memberService;
    private final JavaMailSender javaMailSender;
    private final SendEmailService sendEmailService;


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

    //Email과 name의 일치여부를 check하는 컨트롤러
    @GetMapping("/check/findPw")
    public @ResponseBody Map<String, Boolean> pw_find(String userEmail, String userName){
        Map<String,Boolean> json = new HashMap<>();
        boolean pwFindCheck = memberService.memberEmailCheck(userEmail,userName);

        System.out.println(pwFindCheck);
        json.put("check", pwFindCheck);
        return json;
    }

    //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
    @PostMapping("/check/findPw/sendEmail")
    public @ResponseBody void sendEmail(String userEmail, String userName){
        MailDTO dto = sendEmailService.createMailAndChangePassword(userEmail, userName);
        sendEmailService.mailSend(dto);

    }

  /*  @DeleteMapping("/logout")
    public ResponseEntity<String> logout(
            @AuthenticationPrincipal CustomUserDetailsService customDetails,
            @RequestBody TokenDTO tokenDTO
    ) {

        return ResponseEntity.ok(memberService.logout(tokenDTO.getAccessToken(), customDetails));
    }
*/

}


