package com.kh.team4.service;


import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.PageRequestDTO;
import com.kh.team4.dto.PageResultDTO;
import com.kh.team4.entity.Base;
import com.kh.team4.entity.Board;
import com.kh.team4.entity.Member;
import com.kh.team4.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//재연
//Controller에서 respsitory 직접 호출하지 않도록 설계하기 위해 service생성
//BoardDTO 타입을 파라미터로 전달받고 생성된 게시물의 번호를 반환
@Service
public interface BoardService {

    //게시글 등록
    Long register(BoardDTO dto);

    //BoardDTO를 Board엔티티 타입으로 변환 위해 dtoToEntiy()작성
    default Board dtoToEntity(BoardDTO dto) {
        //작성자
        Member member = Member.builder().mname(dto.getWriterId()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }

    //PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
    default BoardDTO entityToDTO(Board board, Member member, Base base) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(base.getRegDate())
                .modDate(base.getModDate())
                .writerId(member.getMid())
                .hits(board.getHits())
                .build();
        return boardDTO;
    }


    //BoardDTO get(Long bno);

    //void delete(Board board);











}
