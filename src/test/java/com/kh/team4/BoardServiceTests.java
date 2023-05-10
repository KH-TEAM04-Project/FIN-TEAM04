package com.kh.team4;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.PageRequestDTO;
import com.kh.team4.dto.PageResultDTO;
import com.kh.team4.entity.Board;
import com.kh.team4.entity.Member;
import com.kh.team4.repository.BoardRepository;
import com.kh.team4.service.BoardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository repository;





  /*  @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.findAll(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }*/



}