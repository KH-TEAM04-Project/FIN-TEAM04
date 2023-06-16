package com.kh.team4.controller;

import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.dto.TaxrefundDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.service.MemberService;
import com.kh.team4.service.TaxrefundService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "localhost:3000")
@RestController
@RequiredArgsConstructor    // 생성자 주입
@RequestMapping("/tax")
@ToString
public class TaxrefundController {

    private final TaxrefundService service;
    private final MemberService memberService;

    @PostMapping({"/taxrefund"})
    public ResponseEntity<TaxrefundDTO> Taxrefund(@RequestHeader("Authorization") String data) {
        System.out.println("연말정산페이지 진입 + 받은 값 확인 : " + data);
        String atk = data.substring(7);
        System.out.println("토큰 값만 추출 : " + atk);
        return ResponseEntity.ok(service.detail(atk));
    }

    @PostMapping("/intotax")
    public boolean intoCheck(@RequestBody MemberReqDTO memberDTO) {
        System.out.println("연말정산페이지 진입 시 패스워드 확인");
        Long mno = memberDTO.getMno();
        String pwd = memberDTO.getPwd();
        System.out.println("받은 값 확인 : Mno - " + mno + ", Pwd - " + pwd);
        return memberService.confirmpwd(mno, pwd);
    }

    @PostMapping("/CheckDetail")
    public ResponseEntity<?> CheckDetail(@RequestHeader("Authorization") String data) {
        System.out.println("CheckDetail 페이지 진입 + 받은값 R려주실? : " + data);
        String atk = data.substring(7);
        System.out.println("토큰 값만 추출 : " + atk);
        return ResponseEntity.ok(service.checkDetail(atk));
    }
}



