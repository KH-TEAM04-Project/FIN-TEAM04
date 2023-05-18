package com.kh.team4.service;

import com.kh.team4.config.SecurityUtil;
import com.kh.team4.dto.BoardDTO;

import com.kh.team4.dto.TokenDTO;
import com.kh.team4.entity.*;

import com.kh.team4.config.SecurityUtil;
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

import static com.kh.team4.entity.Board.dtoToEntity;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
//재연
public class BoardService {

    @Autowired
    private final BoardRepository repository; //자동주입 final
    private final MemberRepository memberRepository; //자동주입 final

    /* 상세 보기 */
    public BoardDTO oneBoard(Long bno) {
        Board board = repository.findById(bno).orElseThrow(() -> new RuntimeException("글이 없습니다."));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // SecurityUtil에서 SecurityContext에 유저정보가 저장
        if (authentication == null || authentication.getPrincipal() == "anonymousUser") {
            // 인증정보가 없을 경우, 익명유저 값 적용
            return BoardDTO.of(board, false);
            //Board 객체와 false 합쳐서 BoardDTO생성
        } else { //인증정보가 존재할 경우, 인증정보에 있는 id를 추출해 내어 member객체를 찾아내고, Board의 Member객체와 일치 여부 boolean 값을 얻어옴
            Member member = memberRepository.findById(Long.parseLong(authentication.getName())).orElseThrow();
            boolean result = board.getMember().equals(member);
            return BoardDTO.of(board, result);

        }
    }
    /*public Page<PageResponseDto> pageArticle(int pageNum) {
       return articleRepository.searchAll(PageRequest.of(pageNum - 1, 20));
   }*/

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

    /* 게시글 등록 */
/*    @Transactional
    public Long postBoard(BoardDTO dto) {
        log.info("postBoard서비스");
     *//*   Long mno2 = 1L;
        Member member = new Member(mno2);*//*
        Member member = isMemberCurrent();
        log.info("member : " + member);

        //Board board = Board.createBoard(title, content, member);
        Board board = dtoToEntity(dto, member);
        log.info("board : " + board);
        repository.save(board);
        return board.getBno();
        //인증정보에서 Member의 id를 추출해, Member 객체를 생성해내어, Repository를 거쳐 DB로
    }*/

    @Transactional
    public BoardDTO postBoard(BoardDTO boardDTO) {
  /*      Long mno2 = 1L;
        Member member = new Member(mno2);*/
        Member member = isMemberCurrent();
       // Board board = Board.createBoard(bno, title, content, member);
        System.out.println(boardDTO);
        System.out.println(member);
        Board board = dtoToEntity(boardDTO, member);
        System.out.println("board : " + board);
        return BoardDTO.of(repository.save(board), true);
    }

    /* 게시글 수정 */
    @Transactional
    public BoardDTO changeBoard(Long bno, String title, String content) {
        Board board = authorizationArticleWriter(bno);
        return BoardDTO.of(repository.save(Board.changeBoard(board, title, content)), true);
    }

    /* 게시글 삭제 */
    @Transactional
    public void deleteBoard(Long bno) {
        Board board = authorizationArticleWriter(bno);
        repository.delete(board);
    }

    /* 토큰 확인 메서드 : 수정과 삭제에 사용 */
    public Member isMemberCurrent() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()) //getCurrentMemberId
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("member" + member);
        return member;
    }
/*    public Member isMemberCurrent() {
        Long mno = findByMid2() //mid로 mno를 찾아서
        Member member = memberRepository.findById(mno) //mno에 해당하는 member 객체 넣음
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("member" + member);
        return member;
    }*/


    /* 토큰 Member객체와 일치하는지 확인 */
    public Board authorizationArticleWriter(Long bno) {
        //bno로 Board객체 DB에서 불러옴
        Member member = isMemberCurrent();
        Board board = repository.findById(bno).orElseThrow(() -> new RuntimeException("글이 없습니다."));
        if (!board.getMember().equals(member)) {
            throw new RuntimeException("로그인한 유저와 작성 유저가 같지 않습니다.");
        }  //Board객체에서 Member객체 추출하여 토큰에서 추출한 Member객체와 일치하는지 확인
        return board;
    }

    //조회수
    @Transactional //레파지토리에서 쿼리문 지정해줬을 경우 일관성,영속성을 위해 @트랜잭션 사용
    public void updateHits(Long bno) {
        repository.updateHits(bno);
    }


    //게시글 등록
/*    public Long register(BoardDTO dto) {
        log.info("리액트에서 받아온" + dto);
        Board board = dtoToEntity(dto, member);

        log.info("dto -> entity 완료" + board);
        repository.save(board);
        return board.getBno();
    }*/

    //인증정보 없이 게시글 상세조회
/*    public BoardDTO findById(Long bno) {
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
    }*/

    /*    // 인증 정보 없이 글 수정
    @Transactional
    public Long modify(BoardDTO boardDTO) {
        // getOne() : 필요한 순간까지 로딩을 지연하는 방식
        Board board = repository.getOne(boardDTO.getBno());

        board.updateTitle(boardDTO.getTitle());
        board.updateContent(boardDTO.getContent());

        repository.save(board);
        return board.getBno();
    }*/

    /*   //인증정보 없이 게시글 삭제
       public void delete(Long bno) {
           System.out.println("서비스 진입");
           repository.deleteById(bno);
       }*/
}









