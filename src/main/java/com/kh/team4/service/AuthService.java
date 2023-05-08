package com.kh.team4.service;

import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.dto.TokenDto;
import com.kh.team4.entity.Member;
import com.kh.team4.jwt.TokenProvider;
import com.kh.team4.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberResDTO signup(MemberReqDTO reqDto) {
        if (memberRepository.existsByMid(reqDto.getMid())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = reqDto.toMember(passwordEncoder);
        return MemberResDTO.of(memberRepository.save(member));
    }

    public TokenDto login(MemberReqDTO reqDto) {
        UsernamePasswordAuthenticationToken authenticationToken = reqDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authentication);
    }

}