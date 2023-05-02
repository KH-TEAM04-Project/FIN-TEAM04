package com.kh.team4.service;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.entity.Board;
import com.kh.team4.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Log4j2
//재연
public class BoardServiceImpl implements BoardService{

    @Autowired
    private final BoardRepository repository; //자동주입 final


    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);
        Board board = dtoToEntity(dto);
        repository.save(board);
        return board.getBno();
    }

}
