package com.kh.team4.service;

import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    //final 붙여야지 생성자 만들어줌
    private final MemberRepository memberRepository;


    // 로그인 기능 구현
    public MemberResDTO findBy(final MemberReqDTO params){
        MemberResDTO entity = memberRepository.findByMidAndPwd(params.getMid(), params.getPwd());
        return entity;
    }

}

