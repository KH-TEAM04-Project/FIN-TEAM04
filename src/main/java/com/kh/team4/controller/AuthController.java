package com.kh.team4.controller;

import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.dto.TokenDto;
import com.kh.team4.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
//@CrossOrigin(origins = "localhost:3000")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;



    @PostMapping("/sLogin")
    public ResponseEntity<TokenDto> login(MemberReqDTO requestDto) { // RequestBody사용시 에러뜸.
        System.out.println("컨트롤러에 집입하였습니다. " + requestDto.toString());
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberResDTO> signup(@RequestBody MemberReqDTO requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }
}