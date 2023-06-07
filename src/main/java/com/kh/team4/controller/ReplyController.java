package com.kh.team4.controller;

import com.kh.team4.dto.ReplyDTO;
import com.kh.team4.service.QnaService;
import com.kh.team4.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
@Log4j2
public class ReplyController {
    private final ReplyService replyService; // ReplyService 를 주입 받음
    private final QnaService qnaService;

    // 댓글 작성
    @PostMapping("qna/replys/{qno}")
    public ResponseEntity<?> createReply(@PathVariable Long qno, @RequestBody ReplyDTO replyDTO) {
        log.info("댓글컨트롤러 진입");
        System.out.println("replyDTO = " + replyDTO);

        // qno에 해당하는 Qna가 존재하는지 확인
        if (!qnaService.existsQna(qno)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 게시글이 존재하지 않습니다.");
        }
        // replyDTO에 qno 설정
        replyDTO.setQno(qno);

        Long saveResult = replyService.save(replyDTO);
        if (saveResult != null) {
            // 작성 성공하면 댓글목록을 가져와서 리턴
            // 댓글목록: 해당 게시글의 댓글 전체 - 해당게시글 아이디를 기준으로 가져와야 됨.
            List<ReplyDTO> replyDTOList = replyService.findAll(qno);
            log.info("작성 성공");
            return new ResponseEntity<>(replyDTOList, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 작성에 실패하였습니다.");
        }
    }
    // 댓글 리스트 페이지
    @GetMapping("/qna/replys/{qno}")
    public ResponseEntity<?> getReplys(@PathVariable Long qno) {
        List<ReplyDTO> replyDTOList = replyService.findAll(qno);
        log.info("댓글 목록 가져오기");
        return ResponseEntity.ok(replyDTOList);
    }
    // 댓글 삭제 기능
    @DeleteMapping("/qna/replys/delete/{qno}/{rno}")
    public String deleteReply(@PathVariable("qno") Long qno, @PathVariable("rno") Long rno) {
        System.out.println("댓글 삭제 컨트롤러 진입");
        replyService.delete(rno);
        System.out.println("서비스에서 delete 함수 호출");
        return "redirect:/qna/list";
    }
}


