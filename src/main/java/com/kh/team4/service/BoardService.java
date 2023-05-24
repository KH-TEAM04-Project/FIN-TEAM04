
package com.kh.team4.service;

import com.kh.team4.config.SecurityUtil;
import com.kh.team4.dto.BoardDTO;

import com.kh.team4.dto.MemberResDTO;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
//재연
public class BoardService {

    @Autowired
    private final BoardRepository repository; //자동주입 final
    private final MemberRepository memberRepository; //자동주입 final

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

    @Transactional
    public BoardDTO postBoard(BoardDTO boardDTO) {
        System.out.println(boardDTO);
        Board board = dtoToEntity(boardDTO);

        System.out.println("board : " + board);
        repository.save(board);
        return BoardDTO.of(board, true);
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

    // 인증 정보 없이 글 수정
    @Transactional
    public Long modify(BoardDTO boardDTO) {
        // getOne() : 필요한 순간까지 로딩을 지연하는 방식
        Board board = repository.getOne(boardDTO.getBno());
        System.out.println("board" + board);

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        repository.save(board);
        System.out.println("board" + board);

        return board.getBno();
    }

    @Transactional
    //인증정보 없이 게시글 삭제
    public void delete(Long bno) {
        System.out.println("삭제 서비스 진입");
        log.info(bno);
        repository.deleteById(bno);
        log.info(bno + "삭제완료");
    }
/*    public void postBoard(BoardDTO boardDTO, Member member) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if(boardDTO.getBoardFile().isEmpty()) {
            // 첨부 파일이 없는 경우
            // repository 는 기본적으로 entity 클래스만 받아준다.
            Board board = Board.dtoToEntity(boardDTO, member);
            // BoardEntity.toSaveEntity(boardDTO); 를 호출한 결과를 entity 객체로 받아올 수 있음
            repository.save(board);
            // entity 를 세이브 메서드로 넘겨주게 되면은 인서트 쿼리가 나가게됨.
        } else {
            // 첨부 파일이 있는 경우
            // DTO를 Entity로 변환해서 Board 테이블에 저장을 하고 BoardFile 테이블에 저장을하는 작업이 필요함
            Board board = Board.toSaveFile(boardDTO);
            Long saveId = repository.save(board).getBno();
            // getId()를 쓰는 이유: 부모 자식 관계를 맺어놈, 자식 테이블에서는 부모 게시글에 대한 pk 값(board_id)이 필요하기 때문에

            // 자식 entity 기준으로 entity 타입으로 선언 해놨음 pk 값이 아니라 entity 값을 전달해줘야하는 특징이 있음
            Board board2 = repository.findById(saveId).get(); // 옵셔널 생략
            // 부모 entity 자체가 전달이 되어야 하므로 부모 entity 를 db로 부터 가져옴
            // 파일이 여러개인 상황이라 for 문으로 작성
            for(MultipartFile boardFile: boardDTO.getBoardFile()) {
//              1. DTO에 담겨있는 파일을 가져옴
                String originFile = boardFile.getOriginalFilename();  // 2. 사용자가 올린 파일의 이름
                String storedFile = System.currentTimeMillis() + "_" + originFile;    // 3.
                // System.currentTimeMillis() : 1970년 1월 1일 기준으로 현재가 얼만큼 지났느냐의 값이 나옴.
                String savePath = "C:/springboot_img/" + storedFile;   // 폴더를 만들어 놔야됨.
                // C:/springboot_img/9564547645765_내사진.jpg
                boardFile.transferTo(new File(savePath));   // 5. "C:/springboot_img/" 에 파일이 저장됨.

                Files files = Files.toFiles(board, originFile, storedFile);
                // files 객체로 변환하기 위한 작업
                filesRepository.save(files);  // DB에 저장
            }

        }

    }*/

}









