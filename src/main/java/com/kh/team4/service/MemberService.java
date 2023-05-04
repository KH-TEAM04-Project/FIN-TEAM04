package com.kh.team4.service;

import com.kh.team4.dto.MemberReqDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    //final 붙여야지 생성자 만들어줌
    private final MemberRepository memberRepository;


    public MemberResDTO login(MemberReqDTO memberReqDTO) {
        /** 처리과정
         * 1. 회원이 입력한 이메일로 DB에서 조회를 함
         * 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<Member> byMid = memberRepository.findByMid(memberReqDTO.getMid());
        if (byMid.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다.)
            Member member = byMid.get();
            if (member.getPwd().equals(memberReqDTO.getPwd())) {
                // equals 라는 메소드를 반드시 사용해야한다
                // memberEntity에 담겨 있는 패스워외 DTO에 담겨있는 패스워드가 같은지 비교
                // 비밀번호 일치 -> 정보를 컨트롤러 쪽으로 넘겨줘야 함.
                // entity -> dto 변환 후 리턴 (컨트롤러로 넘겨줄때는 dto로 넘김)
                // 이 강의는 entity 객체는 서비스안에서만 사용하게끔 코딩
                MemberResDTO dto = MemberResDTO.toEntity(member);
                System.out.println("로그인 성공");
                return dto;
                // MemberDTO에서 entity를 dto로 변화하여 변환한 dto객체를 리턴

            } else {
                // 비밀번호 불일치(로그인실패)
                System.out.println("비밀번호가 틀렸습니다.");
                return null;
            }
        } else {
            // 조회 결과가 없다(해당 이메일을 가진 회원 정보가 없다.)
            System.out.println("해당 유저가 없습니다.");
            return null;
        }

        // Entity 객체는 Service 안에서만 사용

    }


}

