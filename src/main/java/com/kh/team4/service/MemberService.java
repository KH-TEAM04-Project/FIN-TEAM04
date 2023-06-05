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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional()
public class MemberService {
    //final 붙여야지 생성자 만들어줌
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder managerBuilder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    // 회원가입 기능 구현
    public String regist(MemberReqDTO memberDTO) {
        System.out.println(memberDTO.toString());
        Member entMember = Member.dtoToEntity2(memberDTO, passwordEncoder);
        memberRepository.save(entMember);
        String aaa = "success";
        return aaa;
    }
    public MemberResDTO detail(String atk) {
        System.out.println("Detail Service 진입");
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        MemberResDTO member = MemberResDTO.of2(memberRepository.findById(mno));
        System.out.println("마이페이지로 보낼 값 (3가지만 선정) : " + member.toString());
        return member;
    }

/*    public void delete(Long mno) {
        System.out.println("삭제할 회원의 No. : " + mno);
        System.out.println("받은 값 : " + mno);
        memberRepository.deleteById(mno);
    }*/

    public void delete(String atk) {
        String name = tokenProvider.getAuthentication(atk).getName();
        Long mno = memberRepository.findByMid2(name);
        System.out.println("삭제할 회원의 No. : " + mno);
        memberRepository.deleteById(mno);
        System.out.println(name + "은 임포스터 였습니다.");
    }


    public MemberResDTO Update(MemberReqDTO memberDTO, String atk) throws Exception {
        // 05.12 시점에 수정할만한 컬럼 목록 3가지만 설정
        String email = memberDTO.getEmail();
        String ph = memberDTO.getPh();
        // 찾는 용도의 Mno를 써야하는군..
        Long mno = memberRepository.findByMid2(tokenProvider.getAuthentication(atk).getName());
        String address = memberDTO.getAddress();
        String detailAddress = memberDTO.getDetailAddress();

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

    public boolean changePwd(MemberReqDTO memberDTO, String atk) {
        Long mno = memberRepository.findByMid2(tokenProvider.getAuthentication(atk).getName());
        String resistedPwd = memberRepository.findById(mno).get().getPwd();
        Member member = memberRepository.findById(mno).get();

        if (passwordEncoder.matches(memberDTO.getPwd(), resistedPwd)) {
            String decodedPwd = passwordEncoder.encode(memberDTO.getChangePwd());
            member.setPwd(decodedPwd);
            memberRepository.save(member);
            System.out.println("적용완료");
            return true;
        } else {
            System.out.println("비번틀림");
            return false;
        }
    }

    @Transactional
    public TokenDTO login(MemberReqDTO reqDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = reqDto.toAuthentication();
        System.out.println("여기까지 실행됨");

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        Long midex1 = memberRepository.findByMid2(reqDto.getMid());
        System.out.println("넣은 값 확인 : midex1 = " + midex1);
        TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication, midex1);
        System.out.println("Access 토큰값 확인 " + tokenDto.getAccessToken());

        redisTemplate.opsForValue().set("RT:" + authentication.getName(), tokenDto.getRefreshToken(), tokenDto.getTokenExpiresIn(), TimeUnit.MILLISECONDS);
        System.out.println(redisTemplate.getClientList());
        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDTO reissue(TokenDTO token, String atk) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(token.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(atk);
    
        String refreshToken = redisTemplate.opsForValue().get("RT:" + authentication.getName());

        if (!refreshToken.equals(token.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 만료된 값을 사용하는 코드를 짜 보았다.
        // 블랙리스트 기능을 사용했다고.. 볼수있나?
        // Refresh Token 을 대체하는 Access 토큰 : logout 사용처가 발견되면 해당 코드는 삭제할 것.
        String rediskey = redisTemplate.opsForValue().get(atk);

        if(StringUtils.hasText(rediskey)) {
            // redisTemplate.delete(rediskey);
            // 두 명 이상의 해커가 탈취했을 경우 한놈은 막지만, 삭제하면 다른 한놈은 못막기에
            // 삭제하지 않고 여전히 유지시키는가 보다.
            throw new RuntimeException("해킹하지마라 숨지고싶지 않으면");
        }

        // 5. 새로운 토큰 생성
        Long mno = memberRepository.findByMid2(authentication.getName());
        TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication, mno);

        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenDto.getRefreshToken(),
                        tokenDto.getTokenExpiresIn(), TimeUnit.MILLISECONDS);

        // 토큰 발급
        return tokenDto;
    }

    @Transactional
    public String logout(String token){
        // 로그아웃 하고 싶은 토큰이 유효한 지 먼저 검증하기
        //System.out.println("받은 값 확인 : " + token.getAccessToken());

        if (!tokenProvider.validateToken(token)){
            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(token);

        System.out.println("삭제할 '그'녀석 : " + authentication.getName());

        // Redis에서 해당 User getName 저장된 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제를 한다.
        if (redisTemplate.opsForValue().get("RT:"+authentication.getName())!=null){
            // Refresh Token을 삭제
            redisTemplate.delete("RT:"+authentication.getName());
        }

        // 해당 Access Token 유효시간을 가지고 와서 BlackList에 저장하기
        // 블랙 리스트에 mno 값을 추가하면 로그인 했을 때 기존의 logout 값을 레디스에서 삭제할 수 있을거같은데...
        // 굳이 안하더라도 새벽 4시쯤 매일 서버업뎃한답시고 레디스서버를 조져버리면 해결될 일이기도 하다.
        Long expiration = tokenProvider.getExpiration(token); //(tokenRequestDto.getAccessToken());
        System.out.println("유효시간 값 확인 :" + expiration);
        redisTemplate.opsForValue().set(token,"logout",expiration,TimeUnit.MILLISECONDS);
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

