package com.kh.team4.service;

import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.dto.TaxrefundDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.jwt.TokenProvider;
import com.kh.team4.repository.MemberRepository;
import com.kh.team4.repository.TaxrefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaxrefundService {

    private final TaxrefundRepository repository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;


    public TaxrefundDTO detail(String atk) {
        System.out.println("Detail Service 진입");
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        System.out.println("전달받은 값1 " + mno.toString());
        Member mno2 = new Member(mno);
        System.out.println("전달받은 값2 " + mno2);
        TaxrefundDTO dto = TaxrefundDTO.entityToDTO(repository.findByMno(mno2));
        System.out.println("연말정산 페이지 들어가여: " + dto.toString());
        return dto;
    }
}
