package com.kh.team4.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdPwRequest {

    @PostMapping("/IdPw")
    public ResponseEntity<String> IdPw(@RequestBody IdPwForm idPwForm) {
        return new ResponseEntity<>("아이디여깄다.", HttpStatus.OK);
    }
}
