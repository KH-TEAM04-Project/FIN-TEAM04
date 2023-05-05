package com.kh.team4.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class SignUpRequest {

    @PostMapping("/SignUp")
    public ResponseEntity<String> signUp(@RequestBody SignUpForm signUpForm) {
        // 회원가입 정보 처리 로직 작성
        // ...
        return new ResponseEntity<>("회원가입이 완료되었습니다.", HttpStatus.OK);
    }
}
