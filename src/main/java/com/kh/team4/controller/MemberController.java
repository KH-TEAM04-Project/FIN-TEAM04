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

    @PostMapping("/MyPageCont")
    public ResponseEntity<MemberResDTO> memberDetail(@RequestBody MemberReqDTO memreqDTO) {
        Long mno = memreqDTO.getMno();
        System.out.println("회원정보 페이지 진입 및 받은 값 : " + mno);
        return ResponseEntity.ok(memberService.detail(mno));
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
    @PostMapping("/check/findID")
    public String ID_find(MemberReqDTO memreq) {
        String reqemail = memreq.getEmail();
        String reqname = memreq.getMname();

        return memberService.findID2(reqemail, reqname);

    }

    //아이디 찾기
    @PostMapping("/check/findID2")
    public String find_id(MemberReqDTO requestDto) {
        String email = requestDto.getEmail();
        String mname = requestDto.getMname();
        String result = memberService.findID2(email, mname);

        return result;
    }

    @GetMapping("/check/findPw")
    public @ResponseBody Map<String, Boolean> pw_find(String email, String mname) {
        Map<String, Boolean> json = new HashMap<>();
        boolean pwFindCheck = memberService.memberEmailCheck(email, mname);

        System.out.println(pwFindCheck);
        json.put("check", pwFindCheck);
        return json;
    }

    //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
    @PostMapping("/check/findPw/sendEmail")
    public @ResponseBody void sendEmail(MemberReqDTO requestDto) {
        String email = requestDto.getEmail();
        String mname = requestDto.getMname();
        System.out.println(requestDto.getEmail() + requestDto.getPwd());
        MailDTO dto = sendEmailService.createMailAndChangePassword(email, mname);
        System.out.println(dto.toString());
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


