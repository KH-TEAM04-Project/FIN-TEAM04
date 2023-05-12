package com.kh.team4.service;

import com.kh.team4.config.SecurityUtil;
import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.dto.TokenDTO;
import com.kh.team4.entity.Board;
import com.kh.team4.entity.Member;
import com.kh.team4.entity.RefreshToken;
import com.kh.team4.jwt.TokenProvider;
import com.kh.team4.repository.MemberRepository;
import com.kh.team4.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional()
public class MemberService {
    //final 붙여야지 생성자 만들어줌
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 기능 구현
    public String regist(MemberReqDTO memberDTO) {
        System.out.println(memberDTO.toString());
        Member entMember = Member.dtoToEntity2(memberDTO, passwordEncoder);
        memberRepository.save(entMember);
        String aaa = "회원가입 성공";
        return aaa;
    }

    public void delete(Long mno) {
        System.out.println("받은 값 : " + mno);
        memberRepository.deleteById(mno);
    }

    public MemberResDTO Update(MemberReqDTO memberDTO) throws Exception {
        // 05.12 시점에 수정할만한 컬럼 목록 3가지만 설정
        String password = memberDTO.getPwd();
        String email = memberDTO.getEmail();
        String ph = memberDTO.getPh();
        // 찾는 용도의 Mno를 써야하는군..
        Long mno = memberDTO.getMno();

        Member entMember = Member.dtoToEntity2(memberDTO, passwordEncoder);

        memberRepository.updateMember(password, email, ph);

        Optional<Member> updatingMember = memberRepository.findById(mno); // 업데이트 된 값을 서치해서 다시 가져와서  ResDTO 에 넣고 리턴.
        System.out.println("수정된 값 확인 : " + updatingMember.get().confirm());

        if (updatingMember.isPresent()) {
            System.out.println("존재 확인 작업");
            Member member = updatingMember.get();
            MemberResDTO memberResDTO = MemberResDTO.of(member);
            return memberResDTO;
        } else {
            System.out.println("return null");
            return null;
        }

    }


    private final AuthenticationManagerBuilder managerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;


    @Transactional
    public TokenDTO login(MemberReqDTO reqDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = reqDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        Long midex1 = reqDto.getMno();
         TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication, midex1);
        //TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
        System.out.println("힘들어 뒤질각" + refreshToken);
        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDTO reissue(TokenDTO tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }


    public MemberResDTO getMyInfoBySecurity() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResDTO::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }

    @Transactional
    public MemberResDTO changeMemberPassword(String email, String uname, String exPassword, String newPassword) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        if (!passwordEncoder.matches(exPassword, member.getPwd())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }
        member.setPwd(passwordEncoder.encode((newPassword)));
        return MemberResDTO.of(memberRepository.save(member));
    }
}

