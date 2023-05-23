package com.kh.team4.service;

import com.kh.team4.config.RedisUtil;
import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.dto.TokenDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.jwt.TokenProvider;
import com.kh.team4.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional()
public class MemberService {
    //final 붙여야지 생성자 만들어줌
    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 기능 구현
    public String regist(MemberReqDTO memberDTO) {
        System.out.println(memberDTO.toString());
        Member entMember = Member.dtoToEntity2(memberDTO, passwordEncoder);
        memberRepository.save(entMember);
        String aaa = "success";
        return aaa;
    }

    public MemberResDTO detail(Long mno) {
        MemberResDTO member = MemberResDTO.of2(memberRepository.findById(mno));
        System.out.println("마이페이지로 보낼 값 (3가지만 선정) : " + member.toString());
        return member;
    }


    public void delete(Long mno) {
        System.out.println("받은 값 : " + mno);
        memberRepository.deleteById(mno);
    }


    public MemberResDTO Update(MemberReqDTO memberDTO) throws Exception {
        // 05.12 시점에 수정할만한 컬럼 목록 3가지만 설정
        String email = memberDTO.getEmail();
        String ph = memberDTO.getPh();
        // 찾는 용도의 Mno를 써야하는군..
        Long mno = memberDTO.getMno();
        String address = memberDTO.getAddress();
        String detailAddress = memberDTO.getDetailaddress();

        Member memberorigin = memberRepository.findById(mno).get();

        memberorigin.toUpdate(email, ph, address, detailAddress);

        memberRepository.save(memberorigin);

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

    public boolean changePwd(MemberReqDTO memberDTO) {
        Member entMember = Member.dtoToEntity2(memberDTO, passwordEncoder);
        String password = entMember.getPwd();   // 변환된 pwd
        Member member = memberRepository.findById(memberDTO.getMno()).get();

        if (password.equals(member.getPwd())) {
            member.setPwd(memberDTO.getChangePwd());
            memberRepository.save(member);
            System.out.println("적용완료");
            return true;
        } else {
            System.out.println("비번틀림");
            return false;
        }



    }


    private final AuthenticationManagerBuilder managerBuilder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;


    @Transactional
    public TokenDTO login(MemberReqDTO reqDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = reqDto.toAuthentication();
        System.out.println("여기까지 실행됨");

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        // TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication);

        // id를 기준으로 mno 값 가져오기
        // 상경 데이터 실험 -- mno 매칭 데이터 필요.
        Long midex1 = memberRepository.findByMid2(reqDto.getMid());
        System.out.println("넣은 값 확인 : midex1 = " + midex1);
        TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication, midex1);
        System.out.println("Access 토큰값 확인 " + tokenDto.getAccessToken());

        // 4. RefreshToken 저장
       /* RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
        System.out.println("refreshtoken 생성중" + refreshToken);*/
        // refreshTokenRepository.save(refreshToken);

        redisTemplate.opsForValue().set("RT:" + authentication.getName(), tokenDto.getRefreshToken(), tokenDto.getTokenExpiresIn(), TimeUnit.MILLISECONDS);
        System.out.println(redisTemplate.getClientList());
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
        /*RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));*/

        String refreshToken = redisTemplate.opsForValue().get("refreshToken" + authentication.getName());

        // 4. Refresh Token 일치하는지 검사
        /*if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }*/

        if (!refreshToken.equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }


        // 5. 새로운 토큰 생성
        TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
       /* RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);*/

        redisTemplate.opsForValue()
                .set("RefreshToken:" + authentication.getName(), tokenDto.getRefreshToken(),
                        tokenDto.getTokenExpiresIn(), TimeUnit.MILLISECONDS);

        // 토큰 발급
        return tokenDto;
    }

    @Transactional
    public String logout(TokenDTO logout){
        // 로그아웃 하고 싶은 토큰이 유효한 지 먼저 검증하기
        if (!tokenProvider.validateToken(logout.getAccessToken())){
            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");
        }

        // Access Token에서 User Mid을 가져온다
        // String authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
        Authentication authentication = tokenProvider.getAuthentication(logout.getAccessToken());

        // Redis에서 해당 User getName 저장된 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제를 한다.
        if (redisTemplate.opsForValue().get("RT:"+authentication.getName())!=null){
            // Refresh Token을 삭제
            redisTemplate.delete("RT:"+authentication.getName());
        }

        // 해당 Access Token 유효시간을 가지고 와서 BlackList에 저장하기

        Long expiration = tokenProvider.getExpiration(logout.getAccessToken()); //(tokenRequestDto.getAccessToken());
        redisTemplate.opsForValue().set(logout.getAccessToken(),"logout",expiration,TimeUnit.MILLISECONDS);
        return "로그아웃 완료";
    }


    public boolean memberEmailCheck(String email, String mname) {
        Member member = memberRepository.findByEmail(email);
        if (member != null && member.getMname().equals(mname)) {
            return true;
        } else {
            return false;
        }
        // Email을 통해 해당 email로 가입된 정보가 있는지 확인하고,
        // 가입된 정보가 있다면 입력받은 name과 등록된 name이 일치한지 여부를 리턴하는 메소드
    }






    public String findID2(String email, String mname) {
        System.out.println("아이디 찾기 진행중");
        Optional<Member> findID2 = memberRepository.findByMidwithemailandmname(email, mname);
        MemberResDTO aaa1 = MemberResDTO.of2(findID2);
        String bbb2 = aaa1.getMid();
        System.out.println("찾아낸 아이디 값은 :" + bbb2);
        return bbb2;
    }

    public boolean confirmpwd(Long mno, String pwd) {
        Optional<Member> searchmember = memberRepository.findById(mno);
        String confirmedPwd = searchmember.get().getPwd();
        System.out.println("서치한 패스워드 확인: " + confirmedPwd);
        System.out.println("가져온 패스워드 확인: " + pwd);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean passwordMatches = passwordEncoder.matches(pwd, confirmedPwd);

        if (passwordMatches) {
            System.out.println("비밀번호가 일치합니다.");
            return true;
        } else {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return false;
        }
    }
}

