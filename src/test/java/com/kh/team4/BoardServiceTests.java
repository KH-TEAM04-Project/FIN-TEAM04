package com.kh.team4;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;


    @Test
    public void testRegister() {
        BoardDTO dto = BoardDTO.builder()
                .title("Test.")
                .content("Test...")
                .hits(0)
                .build();
        //boardService.register(dto);


        Long bno = boardService.register(dto);


    }
/*

       @Test
        public void testList() {

            //1페이지 10개
            PageRequestDTO pageRequestDTO = new PageRequestDTO();

            PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

            for (BoardDTO boardDTO : result.getDtoList()) {
                System.out.println(boardDTO);
            }

        }

        @Test
        public void testGet() {

            Long bno = 100L;

            BoardDTO boardDTO = boardService.get(bno);

            System.out.println(boardDTO);
        }

        @Test
        public void testRemove() {

            Long bno = 1L;

            boardService.removeWithReplies(bno);

        }

        @Test
        public void testModify() {

            BoardDTO boardDTO = BoardDTO.builder()
                    .bno(2L)
                    .title("제목 변경합니다.2")
                    .content("내용 변경합니다.2")
                    .build();

            boardService.modify(boardDTO);

        }


        @Test
        public void testSearch(){

            PageRequestDTO pageRequestDTO = new PageRequestDTO();
            pageRequestDTO.setPage(1);
            pageRequestDTO.setSize(10);
            pageRequestDTO.setType("t");
            pageRequestDTO.setKeyword("11");

            Pageable pageable = pageRequestDTO.getPageable(Sort.by("bno").descending());

            PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

            for (BoardDTO boardDTO : result.getDtoList()) {
                System.out.println(boardDTO);
            }
        }

*/


}
