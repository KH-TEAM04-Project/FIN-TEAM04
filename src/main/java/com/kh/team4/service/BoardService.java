
package com.kh.team4.service;

import com.kh.team4.dto.BoardDTO;

import com.kh.team4.entity.*;

import com.kh.team4.repository.BoardRepository;
import com.kh.team4.repository.FilesRepository;
import com.kh.team4.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
//재연
public class BoardService {

    @Autowired
    private final BoardRepository repository; //자동주입 final
    private final MemberRepository memberRepository; //자동주입 final
    private final FilesRepository filesRepository; //자동주입 final


    @Transactional
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

/*    @Transactional
    public BoardDTO postBoard(BoardDTO boardDTO) {
        System.out.println(boardDTO);
        Board board = dtoToEntity(boardDTO);

        System.out.println("board : " + board);
        repository.save(board);
        return BoardDTO.of(board, true);
    }*/

    //조회수
    @Transactional //레파지토리에서 쿼리문 지정해줬을 경우 일관성,영속성을 위해 @트랜잭션 사용
    public void updateHits(Long bno) {
        repository.updateHits(bno);
    }


    //게시글 등록
/*    public Long register(BoardDTO dto) {
        log.info("리액트에서 받아온" + dto);
        Board board = dtoToEntity(dto, member);

        log.info("dto -> entity 완료" + board);
        repository.save(board);
        return board.getBno();
    }*/

    @Transactional
    //인증정보 없이 게시글 상세조회
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

    // 인증 정보 없이 글 수정
    @Transactional
    public Long modify(BoardDTO boardDTO) {
        // getOne() : 필요한 순간까지 로딩을 지연하는 방식
        Board board = repository.getOne(boardDTO.getBno());
        System.out.println("board" + board);

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        repository.save(board);
        System.out.println("board" + board);

        return board.getBno();
    }

    @Transactional
    //인증정보 없이 게시글 삭제
    public void delete(Long bno) {
        System.out.println("삭제 서비스 진입");
        log.info(bno);
        repository.deleteById(bno);
        log.info(bno + "삭제완료");
    }


    public BoardDTO postBoard(BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFiles() == null || boardDTO.getBoardFiles().isEmpty()) {
            // 첨부 파일이 없는 경우
            log.info("첨부파일이 없는 경우 서비스 진입");
            Board board = Board.dtoToEntity(boardDTO);
            log.info("첨부파일 없는 경우 boardDTO 값: " + boardDTO);
            repository.save(board);
            return BoardDTO.of(board, true);
        } else {
            log.info("첨부 파일이 있는 경우 서비스 진입");
            // 6. board_table에 해당 데이터 save 처리 //다중 파일일 경우 먼저 부모 객체 가쟈옴
            Board board = Board.toSaveFile(boardDTO);
            log.info("첨부파일 있는 경우 boardDTO 값: " + boardDTO);
            Long saveBno = repository.save(board).getBno(); //board 저장 후 bno값 가져옴
            Board board1 = repository.findById(saveBno).get(); //bno값 있는 board

            for (MultipartFile boardFile : boardDTO.getBoardFiles()) {
                //3. 서버 저장용 이름 만듦
                String originalFilename = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
                //4. 저장 경로 설정 "C:/dev/workspace/project/3rd_project/upload/" 폴더 만들어주기
                String savePath = "C:/projectFiles/" + storedFileName;
                //5. 해당 경로에 파일 저장
                try (InputStream inputStream = boardFile.getInputStream();
                     OutputStream outputStream = new FileOutputStream(savePath)) {
                    IOUtils.copy(inputStream, outputStream);
                } catch (IOException e) {
                    // 예외 처리
                    e.printStackTrace();
                }
                // 7. board_file_table에 해당 데이터 save 처리 */
                Files files = Files.toFiles(board1, originalFilename, storedFileName);
                filesRepository.save(files); // db에 저장
            }

            return boardDTO;
        }
    }



}









