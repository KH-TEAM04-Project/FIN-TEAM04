package com.kh.team4.service;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.PageRequestDTO;
import com.kh.team4.dto.PageResultDTO;
import com.kh.team4.entity.Base;
import com.kh.team4.entity.Board;
import com.kh.team4.entity.Member;
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

            List<BoardDTO>  boardDTOList = new ArrayList<>();

            for(Board board: boardEntityList) {
                boardDTOList.add(BoardDTO.entityToDTO(board));
            }
            log.info(boardDTOList);
            return boardDTOList;
        }

    public void modify(BoardDTO boardDTO) {
        // getOne() : 필요한 순간까지 로딩을 지연하는 방식
        Board board = repository.getOne(boardDTO.getBno());

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        repository.save(board);
    }

    /*@Override
    public void delete(Long id) {
        postRepository.findById(id).ifPresent(p -> {
            if (p.getUser().getId() != SecurityUtil.getCurrentUserLogin().get().getId()) {
                throw new BadRequestException("It's not a writer.");
            }
            postRepository.delete(p);
        });
    }*/

}






