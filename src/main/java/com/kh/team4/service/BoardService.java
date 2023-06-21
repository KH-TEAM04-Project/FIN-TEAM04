
package com.kh.team4.service;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.entity.Board;
import com.kh.team4.jwt.TokenProvider;
import com.kh.team4.repository.BoardRepository;
import com.kh.team4.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
//재연
public class BoardService {

    @Autowired
    private final BoardRepository repository; //자동주입 final
    private final MemberRepository memberRepository; //자동주입 final
    private final TokenProvider tokenProvider;
@Transactional //글 등록
    public void register(BoardDTO boardDTO, String atk) {
        log.info("리액트에서 받아온" + boardDTO);
        Board board = Board.dtoToEntity(boardDTO);
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        log.info("entity 변환 완료" + board);
        repository.save(board);
        log.info("entity 저장 완료" + board);

    }
    //공지사항 목록
    @Transactional
    public List<BoardDTO> findAll() {
        log.info("서비스 진입");
        List<Board> boardEntityList = repository.findAll();
        log.info(boardEntityList);

        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (Board board : boardEntityList) {
            boardDTOList.add(BoardDTO.entityToDTO(board));
        }
        log.info("컨트롤러로 리턴");
        return boardDTOList;
    }
    //조회수
    @Transactional //레파지토리에서 쿼리문 지정해줬을 경우 일관성,영속성을 위해 @트랜잭션 사용
    public void updateHits(Long bno) {
        repository.updateHits(bno);
    }

    //게시글 상세조회
    @Transactional
    public BoardDTO findById(Long bno, String atk) {
        System.out.println(" findById 서비스 진입 ");
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        MemberResDTO member = MemberResDTO.of2(memberRepository.findById(mno));
        Optional<Board> optionalBoard = repository.findById(bno);
        if (optionalBoard.isPresent()) {
            System.out.println("if문 진입");
            Board board = optionalBoard.get();
            BoardDTO boardDTO = BoardDTO.entityToDTO(board);
            System.out.println("boardDTO" + boardDTO);
            return boardDTO;
        } else {
            System.out.println("return null");
            return null;
        }
    }

    // 인증 정보 없이 글 수정
    @Transactional
    public Long modify(BoardDTO boardDTO, String atk) {
        // getOne() : 필요한 순간까지 로딩을 지연하는 방식
        Board board = repository.getOne(boardDTO.getBno());
        System.out.println("수정 전 board" + board);
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        MemberResDTO member = MemberResDTO.of2(memberRepository.findById(mno));

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        repository.save(board);
        System.out.println("수정 후 board" + board);

        return board.getBno();
    }

    //인증정보 없이 게시글 삭제
    @Transactional
    public void delete(Long bno, String atk) {
        System.out.println("삭제 서비스 진입");
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        MemberResDTO member = MemberResDTO.of2(memberRepository.findById(mno));
        log.info(bno);
        repository.deleteById(bno);
        log.info(bno +"삭제완료");
    }
}









