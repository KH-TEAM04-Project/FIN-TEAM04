package com.kh.team4.service;


import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.PageRequestDTO;
import com.kh.team4.dto.PageResultDTO;
import com.kh.team4.entity.Base;
import com.kh.team4.entity.Board;
import com.kh.team4.entity.Member;
import com.kh.team4.repository.BoardRepository;

//재연
//Controller에서 respsitory 직접 호출하지 않도록 설계하기 위해 service생성
//BoardDTO 타입을 파라미터로 전달받고 생성된 게시물의 번호를 반환
public interface BoardService {
    Long register(BoardDTO dto);

    //PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    //BoardDTO get(Long bno);


    //BoardDTO를 Board엔티티 타입으로 변환 위해 dtoToEntiy()작성
    default Board dtoToEntity(BoardDTO dto) {
        //작성자
        Member member = Member.builder().mname(dto.getWriterName()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }

  default BoardDTO entityToDTO(Board board, Member member, Base base) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(base.getRegDate())
                .modDate(base.getModDate())
                .writerName(member.getMname())
                .hits(board.getHits())
                .build();
        return boardDTO;
    }


}
