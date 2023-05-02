package com.kh.team4.service;

import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    //final 붙여야지 생성자 만들어줌
    private final MemberRepository memberRepository;


    // 로그인 기능 구현
    public MemberResDTO findBy(final MemberResDTO params){
        MemberResDTO entity = memberRepository.findByMidAndPwd(params.getMid(), params.getPwd());
        return entity;
    }

}

