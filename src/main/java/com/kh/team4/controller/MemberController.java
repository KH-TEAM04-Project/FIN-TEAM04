package com.kh.team4.controller;

import com.kh.team4.dto.MailDTO;
import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.dto.TokenDTO;
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

    @PostMapping("/intoMyPage") // 마이페이지 진입 전 확인
    public boolean intoCheck(@RequestBody MemberReqDTO memberDTO) {
        System.out.println("마이페이지 진입 시 패스워드 확인");
        Long mno = memberDTO.getMno();
        String pwd = memberDTO.getPwd();
        System.out.println("받은 값 확인 : Mno - " + mno + ", Pwd - " + pwd);
        return memberService.confirmpwd(mno, pwd);
    }

    @PostMapping("/MyPageCont") // 마이페이지
    public ResponseEntity<MemberResDTO> memberDetail(@RequestBody MemberReqDTO memreqDTO) {
        Long mno = memreqDTO.getMno();
        System.out.println("회원정보 페이지 진입 및 받은 값 : " + mno);
        return ResponseEntity.ok(memberService.detail(mno));
    }

    @PostMapping("/memberDelete")   // 회원 삭제
    public void memberDelete(@RequestBody MemberReqDTO memberDTO) {
        // mid 가 아닌 mno를 기준으로 삼은 것은 delete메서드가 ID를 기본 골자로 삼고 있기 때문이다.
        Long mno = memberDTO.getMno();
        System.out.println("삭제할 회원의 No. : " + mno);
        memberService.delete(mno);
    }

    @PostMapping("/memberUpdate")   // 회원 정보 갱신
    public ResponseEntity<MemberResDTO> memberUpdate(@RequestBody MemberReqDTO memberReqDTO) throws Exception {
        System.out.println("받은 값 확인 : " + memberReqDTO.toString());
        return ResponseEntity.ok(memberService.Update(memberReqDTO));
    }


    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO> reissue(@RequestBody TokenDTO reissue) {
        return ResponseEntity.ok(memberService.reissue(reissue));
    }

    @PostMapping("/sLogin")
    public ResponseEntity<TokenDTO> login(MemberReqDTO requestDto) { // RequestBody사용시 에러뜸.
        System.out.println("컨트롤러에 집입하였습니다. " + requestDto.toString());
        return ResponseEntity.ok(memberService.login(requestDto));
    }

    //Email과 name의 일치여부를 check하는 컨트롤러
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

    @DeleteMapping("/logout")
    public String logout(@AuthenticationPrincipal CustomUserDetailsService customDetails, @RequestBody TokenDTO tokenDTO) {
        return memberService.logout(tokenDTO);
    }
}


