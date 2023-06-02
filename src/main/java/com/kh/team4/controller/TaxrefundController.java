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
@RequestMapping()
@ToString
public class TaxrefundController {

    private final TaxrefundService service;
    private final MemberService memberService;

    @PostMapping({"/taxrefund"})
    public ResponseEntity<TaxrefundDTO> Taxrefund(@RequestBody TaxrefundDTO dto) {
        Long mno = dto.getMno();
        System.out.println("연말정산 페이지 진입 및 받은 값 : " + mno);
        return ResponseEntity.ok(service.detail(mno));
    }

    @PostMapping("/intoTaxrefund")
    public boolean intoCheck(@RequestBody MemberReqDTO memberDTO) {
        System.out.println("연말정산페이지 진입 시 패스워드 확인");
        Long mno = memberDTO.getMno();
        String pwd = memberDTO.getPwd();
        System.out.println("받은 값 확인 : Mno - " + mno + ", Pwd - " + pwd);
        return memberService.confirmpwd(mno, pwd);
    }
}



