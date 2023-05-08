package com.kh.team4;

import com.kh.team4.entity.Board;
import com.kh.team4.entity.Member;
import com.kh.team4.repository.BoardRepository;
import com.kh.team4.repository.MemberRepository;
import com.kh.team4.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;
    private MemberRepository memberRepository;

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

    @Test
    public void testReadWithWriter(){
        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[]) result;

        System.out.println("---------------");
        System.out.println(Arrays.toString(arr));
    }

/*    @Test
    public void testTwoWayMapping() {
        Member member = memberRepository.findById(5L).get();

        System.out.println("================================");
        System.out.println(member);
        System.out.println("================================");
     *//*   List<Board> list = member.getBoardList();
        for (Board board : list) {
            System.out.println(board.toString());
        }*//*
    }*/



}