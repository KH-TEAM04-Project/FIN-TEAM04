package com.kh.team4.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.util.IOUtils;
import com.kh.team4.dto.*;
import com.kh.team4.jwt.JwtFilter;
import com.kh.team4.jwt.TokenProvider;
import com.kh.team4.repository.MemberRepository;
import com.kh.team4.service.MemberService;
import com.kh.team4.service.S3Uploader;
import com.kh.team4.service.SendEmailService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "localhost:3000")
@RestController
@RequiredArgsConstructor    // 생성자 주입
@RequestMapping("/member")
@ToString
public class MemberController {
    private final JavaMailSender javaMailSender;
    private final SendEmailService sendEmailService;
    private final S3Uploader s3Uploader;
    private final MemberService memberService;


    @PostMapping("/intoMyPage")
    public boolean intoCheck(@RequestBody MemberReqDTO memberDTO) {
        System.out.println("마이페이지 진입 시 패스워드 확인");
        Long mno = memberDTO.getMno();
        String pwd = memberDTO.getPwd();
        System.out.println("받은 값 확인 : Mno - " + mno + ", Pwd - " + pwd);
        return memberService.confirmpwd(mno, pwd);
    }

    @PostMapping("/MyPageCont")
    public ResponseEntity<MemberResDTO> memberDetail(@RequestHeader("Authorization") String data) {
        System.out.println("마이페이지 진입 + 받은 값 확인 : " + data);
        String atk = data.substring(7);
        System.out.println("토큰 값만 추출 : " + atk);
        return ResponseEntity.ok(memberService.detail(atk));
    }

    @PostMapping("/memberUpdate")
    public ResponseEntity<MemberResDTO> memberUpdate(@RequestBody MemberReqDTO memberReqDTO, @RequestHeader("Authorization") String data) throws Exception {
        System.out.println("마이페이지 진입 + 받은 값 확인 : " + data);
        System.out.println("수정할 값 확인 : " + memberReqDTO.toString());
        String atk = data.substring(7);
        System.out.println("토큰 값만 추출 : " + atk);
        return ResponseEntity.ok(memberService.Update(memberReqDTO, atk));
    }

    @PostMapping("/profilePhoto")
    public ResponseEntity<?> uploadProfilePhoto(@RequestHeader("Authorization") String data,  @RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        System.out.println("받은 값 확인 : " + data);
        String atk = data.substring(7);
        System.out.println("토큰 값만 추출 : " + atk);

        System.out.println("MultipartFile" + multipartFile);
        //S3 Bucket 내부에 "/profile"
        FileUploadResDTO profile = s3Uploader.upload( multipartFile, "profile", atk);
        return ResponseEntity.ok(profile);
    }


    @PostMapping("/changePassword")
    public boolean changePwd(@RequestBody MemberReqDTO memberDTO, @RequestHeader("Authorization") String data) {
        System.out.println("마이페이지 진입 + 받은 값 확인 : " + data);
        String atk = data.substring(7);
        System.out.println("토큰 값만 추출 : " + atk);
        System.out.println("받은 값 확인 : Mno - " + memberDTO.getMno() + ", 현재 패스워드 - " + memberDTO.getPwd() + ", 변경할 패스워드 - " + memberDTO.getChangePwd());
        return memberService.changePwd(memberDTO, atk);
    }

    @DeleteMapping("/memberDelete")
    public void memberDelete(@RequestHeader("Authorization") String data) {
        System.out.println("회원 삭제 컨트롤러 입장 + 받은 값 확인 : " + data);
        String atk = data.substring(7);
        System.out.println("토큰 값만 추출 : " + atk);
        memberService.delete(atk);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO> reissue(@RequestHeader("Authorization") String data, @RequestBody TokenDTO token) {
        System.out.println("[INFO ] Reissue 컨트롤러 입장 + 받은 값 확인 : " + data);
        String atk = data.substring(7);
        System.out.println("atk : " + atk);
        System.out.println("rtk : " + token.getRefreshToken());
        return ResponseEntity.ok(memberService.reissue(token, atk));
    }

    @PostMapping("/logout22")
    public String logout(@RequestHeader("Authorization") String data) {
        System.out.println("[INFO ] ---------- 로그아웃 컨트롤러 진입");
        System.out.println("받은 데이터 값 : " + data);
        String atk = data.substring(7);
        System.out.println("추출한 ATK : " + atk);
        return memberService.logout(atk);
    }


/*
    @GetMapping("/check/findPw")
    public @ResponseBody Map<String, Boolean> pw_find(String email, String mname) {
        Map<String, Boolean> json = new HashMap<>();
        boolean pwFindCheck = memberService.memberEmailCheck(email, mname);

        System.out.println(pwFindCheck);
        json.put("check", pwFindCheck);
        return json;
    }
*/

/* 미리 생성
    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO> reissue(@RequestHeader("Authorization") String AccessToken) {
        return ResponseEntity.ok(memberService.reissue(AccessToken);
    }*/
    

/*    @PostMapping("/sLogin")
    public ResponseEntity<TokenDTO> login(MemberReqDTO requestDto) { // RequestBody사용시 에러뜸.
        System.out.println("컨트롤러에 집입하였습니다. " + requestDto.toString());
        return ResponseEntity.ok(memberService.login(requestDto));
    }*/

    //Email과 name의 일치여부를 check하는 컨트롤러
/*    @PostMapping("/check/findID")
    public String ID_find(@RequestBody MemberReqDTO memreq) {
        String email = memreq.getEmail();
        String mname = memreq.getMname();
        System.out.println(mname + email);
        return memberService.findID2(email, mname);
    }*/

    //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
/*    @PostMapping("/check/findPw/sendEmail")
    public @ResponseBody void sendEmail(MemberReqDTO requestDto) {
        String email = requestDto.getEmail();
        String mname = requestDto.getMname();
        System.out.println(requestDto.getEmail() + requestDto.getPwd());
        MailDTO dto = sendEmailService.createMailAndChangePassword(email, mname);
        System.out.println(dto.toString());
        sendEmailService.mailSend(dto);
    }*/

/*    @PostMapping("/SignUp2")
    public String memberregist(@RequestBody MemberReqDTO memberDTO) {
        System.out.println(memberDTO.toString());
        return memberService.regist(memberDTO);
    }*/
}


