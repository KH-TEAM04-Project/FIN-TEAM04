package com.kh.team4.service;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.PageRequestDTO;
import com.kh.team4.dto.PageResultDTO;
import com.kh.team4.entity.Board;
import com.kh.team4.entity.Member;
import com.kh.team4.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
@RequiredArgsConstructor
@Log4j2
//재연
public class BoardServiceImpl implements BoardService{

    @Autowired
    private final BoardRepository repository; //자동주입 final

    @Override
    public Long register(BoardDTO dto) {
        log.info("리액트에서 받아온" + dto);
        Board board = dtoToEntity(dto);

        log.info("dto -> entity 완료" + board);
        repository.save(board);
        return board.getBno();
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
