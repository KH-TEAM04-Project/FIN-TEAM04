package com.kh.team4;

import com.kh.team4.entity.Board;
import com.kh.team4.entity.Member;
import com.kh.team4.repository.BoardRepository;
import com.kh.team4.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {

        IntStream.rangeClosed(1,100).forEach(i -> {

            Board board = Board.builder()
                    .title("Title..."+i)
                    .content("Content...." + i)
                    .build();

            boardRepository.save(board);

        });

    }



}