package com.kh.team4.controller;

import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.TokenDTO;
import com.kh.team4.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@CrossOrigin(origins = "localhost:3000")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO> reissue(@RequestBody TokenDTO tokenReqDTO) {
        return ResponseEntity.ok(authService.reissue(tokenReqDTO));
    }

    @PostMapping("/sLogin")
    public ResponseEntity<TokenDTO> login(MemberReqDTO requestDto) { // RequestBody사용시 에러뜸.
        System.out.println("컨트롤러에 집입하였습니다. " + requestDto.toString());
        return ResponseEntity.ok(authService.login(requestDto));
    }

  /*  @PostMapping("/signup")
    public ResponseEntity<MemberResDTO> signup(@RequestBody MemberReqDTO requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }*/
}