package com.kh.team4.service;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.PageRequestDTO;
import com.kh.team4.dto.PageResultDTO;
import com.kh.team4.dto.QnaDTO;
import com.kh.team4.entity.Base;
import com.kh.team4.entity.Board;
import com.kh.team4.entity.Member;
import com.kh.team4.entity.Qna;
import com.kh.team4.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.kh.team4.dto.BoardDTO.entityToDTO;
import static com.kh.team4.entity.Board.dtoToEntity;


@Service
@RequiredArgsConstructor
@Log4j2
//재연
public class BoardService {

    @Autowired
    private final BoardRepository repository; //자동주입 final

    public Long register(BoardDTO dto) {
        log.info("리액트에서 받아온" + dto);
        Board board = dtoToEntity(dto);

        log.info("dto -> entity 완료" + board);
        repository.save(board);
        return board.getBno();
    }

    //작성자 없이
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

    public void delete(Long bno) {
        System.out.println("서비스 진입");
        repository.deleteById(bno);
    }


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

    @Transactional //레파지토리에서 쿼리문 지정해줬을 경우 일관성,영속성을 위해 @트랜잭션 사용
    public void updateHits(Long bno) {
        repository.updateHits(bno);

    }

    public Long update(BoardDTO boardDTO) {
        log.info("board DTO"+boardDTO);
        Board board = Board.toUpdateEntity(boardDTO);
        repository.save(board);
        log.info("board: "+board);
        return board.getBno();
    }


}






