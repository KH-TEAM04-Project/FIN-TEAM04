package com.kh.team4.controller;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.FileUploadResDTO;
import com.kh.team4.service.BoardService;
import com.kh.team4.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor    // 생성자 주입
@CrossOrigin(origins = "http://localhost:3000") //CORS 문제 해결 위함
@RequestMapping("/board") //board로 변경

public class BoardController {
    private final BoardService service;
    private final S3Uploader s3Uploader;

    @PostMapping("/regist")
    public ResponseEntity<String> register(@RequestHeader("Authorization") String data, @RequestParam("multipartFile") MultipartFile multipartFile, @RequestBody BoardDTO boardDTO) throws IOException {
        System.out.println("게시글 작성 컨트롤러 진입");
        String atk = data.substring(7);
        System.out.println("atk : " + atk);
        System.out.println("MultipartFile" + multipartFile);
        //S3 Bucket 내부에 "/profile"
        FileUploadResDTO profile = s3Uploader.upload( multipartFile, "profile", atk);
        System.out.println("profile의 최종값은?" + profile.getProfilePhoto());
        service.register(boardDTO, atk);
        System.out.println("boardDTO:" + boardDTO);
        return new ResponseEntity<>("게시글이 작성되었습니다.", HttpStatus.OK);
    }

    //게시글 목록
    @GetMapping("/list")
    public List<BoardDTO> boardList(@RequestHeader("Authorization") String data) {
        System.out.println("컨트롤러 진입");
        String atk = data.substring(7);
        System.out.println("atk : " + atk);
        List<BoardDTO> boardDTOList = service.findAll();
        System.out.println("BoardDTOList" + boardDTOList);
        return boardDTOList;
    }

    //게시글 상세조회/수정 불러오기
    @GetMapping({"/detail/{bno}", "/update/{bno}"})
    public ResponseEntity<BoardDTO> getOneBoard(@RequestHeader("Authorization") String data, @PathVariable("bno") Long bno) {
        log.info("상세페이지/수정 컨트롤러");
        String atk = data.substring(7);
        System.out.println("atk : " + atk);
        service.updateHits(bno);
        return ResponseEntity.ok(service.findById(bno, atk));
    }


    //게시글 수정 등록
    @PostMapping("/update/{bno}")
        public ResponseEntity<Long> update(@RequestHeader("Authorization") String data, @PathVariable Long bno, @RequestBody BoardDTO dto) {
        log.info("업데이트 컨트롤러 진입");
        //새로 추가된 엔티티의 번호
        String atk = data.substring(7);
        System.out.println("atk : " + atk);
        dto.setBno(bno);
        log.info("수정 dto: " + dto);
        Long updateBno = service.modify(dto, atk);
        log.info("수정 완료 BNO: " + updateBno);
        return ResponseEntity.ok(updateBno);
    }

    //게시글 삭제
    @DeleteMapping("/delete/{bno}")
    public String delete(@RequestHeader("Authorization") String data,@PathVariable("bno") Long bno) {
        System.out.println("삭제 컨트롤러 진입");
        String atk = data.substring(7);
        System.out.println("atk : " + atk);
        service.delete(bno, atk);
        System.out.println("서비스에서 delete 함수 호출");
        return "/delete";
    }
}