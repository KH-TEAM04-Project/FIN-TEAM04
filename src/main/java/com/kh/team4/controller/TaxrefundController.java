package com.kh.team4.controller;

import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.dto.TaxrefundDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.service.MemberService;
import com.kh.team4.service.TaxrefundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public class TaxrefundController {

    TaxrefundService service;
    MemberService memberService;

    @PostMapping({"/taxrefund/detail"})
    public ResponseEntity<TaxrefundDTO> Taxrefund(@RequestBody TaxrefundDTO dto) {
        Member mno = dto.getMno();
        System.out.println("연말정산 페이지 진입 및 받은 값 : " + mno);
        return ResponseEntity.ok(service.detail(mno.getMno()));
    }

    @PostMapping("/taxrefund/intoTaxrefund")
    public boolean intoCheck(@RequestBody MemberReqDTO memberDTO) {
        System.out.println("연말정산페이지 진입 시 패스워드 확인");
        Long mno = memberDTO.getMno();
        String pwd = memberDTO.getPwd();
        System.out.println("받은 값 확인 : Mno - " + mno + ", Pwd - " + pwd);
        return memberService.confirmpwd(mno, pwd);
    }
}



