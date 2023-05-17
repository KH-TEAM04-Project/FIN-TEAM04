package com.kh.team4.controller;

import com.kh.team4.dto.ReplyDTO;
import com.kh.team4.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Log4j2
public class ReplyController {
    private final ReplyService replyService; // ReplyService 를 주입 받음

    @PostMapping("QnaReadPage/{qno}")
    public ResponseEntity<?> createReply(@RequestBody ReplyDTO replyDTO) {
        log.info("댓글컨트롤러 진입");
        System.out.println("replyDTO = " + replyDTO);
        Long saveResult = replyService.save(replyDTO);
        if (saveResult != null) {
            // 작성 성공하면 댓글목록을 가져와서 리턴
            // 댓글목록: 해당 게시글의 댓글 전체 - 해당게시글 아이디를 기준으로 가져와야 됨.
            List<ReplyDTO> replyDTOList = replyService.findAll(replyDTO.getQnaQno());
            log.info("작성 성공");
            return new ResponseEntity<>(replyDTOList, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 게시글이 존재하지 않습니다.");
        }
    }

}


