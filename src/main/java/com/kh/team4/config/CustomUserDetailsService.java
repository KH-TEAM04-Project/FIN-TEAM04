package com.kh.team4.config;

import com.kh.team4.entity.Member;
import com.kh.team4.repository.MemberRepository;
import com.kh.team4.service.MemberService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;
    
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByMid(username);
        Member member = memberOptional.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        // CustomUserDetails 객체를 생성하여 필요한 정보를 설정합니다.
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setMemberId(member.getMno());
        // 다른 UserDetails 필드들을 설정합니다.

        return userDetails;
    }

}