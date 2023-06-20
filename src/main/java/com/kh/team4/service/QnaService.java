package com.kh.team4.service;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.dto.QnaDTO;
import com.kh.team4.entity.Board;
import com.kh.team4.entity.Qna;
import com.kh.team4.jwt.TokenProvider;
import com.kh.team4.repository.MemberRepository;
import com.kh.team4.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
public class QnaService {
    private final QnaRepository qnaRepository;

    private final TokenProvider tokenProvider;

    private final MemberRepository memberRepository;

    public void register(QnaDTO qnaDTO, String atk) {
        log.info("리액트에서 받아온" + qnaDTO);
        Qna qna = Qna.dtoToEntity(qnaDTO);
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        MemberResDTO member = MemberResDTO.of2(memberRepository.findById(mno));
        log.info("entity 변환 완료" + qna);
        qnaRepository.save(qna);
        log.info("entity 저장 완료" + qna);

    }

    public void delete(Long qno, String atk) {
        System.out.println("게시글 삭제 서비스 진입");
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        MemberResDTO member = MemberResDTO.of2(memberRepository.findById(mno));
        qnaRepository.deleteById(qno);
    }

    @Transactional  // 부모 entity 에서 자식 entity 로 접근할때는 그 내용을 호출하는 메서드에서
    // findById 에서 toBoardDTO 를 호출하고 있음 toBoardDTO 안에서
    // boardEntity 가 boardFileEntity 를 접근하고 있기 떄문에 트렌젝션을 붙여줘야됨.
    public QnaDTO findById(Long qno, String atk) {
        System.out.println(" findById 서비스 진입 ");
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        MemberResDTO member = MemberResDTO.of2(memberRepository.findById(mno));
        Optional<Qna> optionalQna = qnaRepository.findById(qno);
        if ( optionalQna.isPresent()) {
            System.out.println("if문 진입");
            Qna qna = optionalQna.get();
            QnaDTO qnaDTO = QnaDTO.toQnaDTO(qna);
            System.out.println("qnaDTO" + qnaDTO);

            return qnaDTO;
        } else {
            System.out.println("return null");
            return null;
        }
    }
    @Transactional  // toBoardDTO 를 사용하고 있기 때문에
    public List<QnaDTO> findAll() {
        System.out.println("qnaservice 진입");
        List<Qna> qnaEntityList = qnaRepository.findAll();
        System.out.println("repository에서 가져오는중");
        // findAll 을 하게 되면, repository 뭔가를 가져올때는 무조건 entity 로 온다.
        // List<BoardEntity> -> list 형태의 entity 가 넘어오게 됨.

        // entity 로 넘어온 객체를 dto 객체로 옮겨 담아서 다시 컨트롤러로 리턴을 해줘야됨
        List<QnaDTO> qnaDTOList = new ArrayList<>(); // 리턴할 객체 선언
        System.out.println("리턴할 객체 선언");
        for (Qna qna: qnaEntityList) {
            // 반복문을 돌려 DTO 리스트에 하나씩 담는다.
            qnaDTOList.add(QnaDTO.toQnaDTO(qna));
            System.out.println("DTO를 변환해서 list에 담기 완료");
            // 반복으로 접근한 entity 객체를 DTO 로 변환을하고 변환된 객체를 list에 담는 작업.
        }
        System.out.println("for문 완료, 컨트롤러로 리턴");
        return qnaDTOList;    // for 문이 끝나면 리스트를 컨트롤러로 리턴해준다.
    }
    // 수정 관련
    @Transactional
    public Long modify(QnaDTO qnaDTO, String atk) {
        // getOne() : 필요한 순간까지 로딩을 지연하는 방식
        Qna qna = qnaRepository.getOne(qnaDTO.getQno());
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        MemberResDTO member = MemberResDTO.of2(memberRepository.findById(mno));

        qna.updateTitle(qnaDTO.getTitle());
        qna.updateContent(qnaDTO.getContent());

        qnaRepository.save(qna);
        return qna.getQno();
    }
    // 조회수 처리 메소드
    @Transactional
    public void updateHits(Long qno) {
        qnaRepository.updateHits(qno); // 레파지토리에서 업데이트히츠 메소드를 호출

    }

    // 게시글 존재 여부 확인
    public boolean existsQna(Long qno) {
        return qnaRepository.existsById(qno);
    }
}
