package com.kh.team4.service;

import com.kh.team4.dto.BoardDTO;

import com.kh.team4.dto.PageResponseDTO;
import com.kh.team4.dto.QnaDTO;
import com.kh.team4.entity.*;
import com.kh.team4.repository.BoardRepository;
import com.kh.team4.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.kh.team4.entity.Board.dtoToEntity;


@Service
@RequiredArgsConstructor
@Log4j2
//재연
public class BoardService {

    @Autowired
    private final BoardRepository repository; //자동주입 final
    private final MemberRepository memberRepository; //자동주입 final

    //게시글 등록
    public Long register(BoardDTO dto) {
        log.info("리액트에서 받아온" + dto);
        Board board = dtoToEntity(dto);

        log.info("dto -> entity 완료" + board);
        repository.save(board);
        return board.getBno();
    }


    //게시글 목록 작성자 없이
    public List<BoardDTO> findAll() {
        log.info("서비스 진입");

        List<Board> boardEntityList = repository.findAll();
        log.info(boardEntityList);

        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (Board board : boardEntityList) {
            boardDTOList.add(BoardDTO.entityToDTO(board));
        }
        log.info(boardDTOList);
        return boardDTOList;
    }
    //게시글 삭제
    public void delete(Long bno) {
        System.out.println("서비스 진입");
        repository.deleteById(bno);
    }

    //게시글 상세조회
    public BoardDTO findById(Long bno) {
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
/*    public BoardDTO findById (Long id) {

        Board board = repository.findById(id).orElseThrow(() -> new RuntimeException("글이 없습니다."));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // SecurityUtil에서 SecurityContext에 유저정보가 저장
        if (authentication == null || authentication.getPrincipal() == "anonymousUser") {
            // 인증정보가 없거나, 익명유저 값일 경우
            return BoardDTO.entityToDTO(board, false);
            //Board 객체와 false 합쳐서 BoardDTO생성
        } else { //인증정보가 존재할 경우, 인증정보에 있는 id를 추출해 내어 member객체를 찾아내고, Board의 Member객체와 일치 여부 boolean 값을 얻어옴
            Member member = memberRepository.findById(Long.parseLong(authentication.getName())).orElseThrow();
            boolean result = board.getMember().equals(member);
            return BoardDTO.entityToDTO(board, result);
        }
    }*/
    //조회수
    @Transactional //레파지토리에서 쿼리문 지정해줬을 경우 일관성,영속성을 위해 @트랜잭션 사용
    public void updateHits(Long bno) {
        repository.updateHits(bno);
    }
    //글 수정
    @Transactional
    public Long modify(BoardDTO boardDTO) {
        // getOne() : 필요한 순간까지 로딩을 지연하는 방식
        Board board = repository.getOne(boardDTO.getBno());

        board.updateTitle(boardDTO.getTitle());
        board.updateContent(boardDTO.getContent());

        repository.save(board);
        return board.getBno();
    }

}






