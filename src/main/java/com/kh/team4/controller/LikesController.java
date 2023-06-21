package com.kh.team4.controller;

import com.kh.team4.dto.LikesDTO;
import com.kh.team4.service.LikesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
@RequestMapping("/qna")
@Log4j2
@CrossOrigin(origins = "http://localhost:3000")
public class LikesController {
    private final LikesService likesService;

    @Autowired
    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    // 좋아요 개수 조회 컨트롤러
    @GetMapping("/likes/{qno}")
    public int getLikesCount(@RequestHeader("Authorization") String data, @PathVariable Long qno) {
        log.info("좋아요 개수 조회 컨트롤러 진입");
        String atk = data.substring(7);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("likeCount", likesService.getLikesCount(qno, atk));
//        map.put("mnoList", likesService.getMnoList(qno));
//        System.out.println("sadfsdfsdfsdf");
//        System.out.println(likesService.getMnoList(qno));
        return likesService.getLikesCount(qno, atk);
    }

    // 좋아요 컨트롤러
    @PostMapping("/addLike/{qno}")
    public void addLike(@RequestHeader("Authorization") String data, @RequestBody LikesDTO likesDTO) {
        log.info("좋아요 컨트롤러 진입");
        String atk = data.substring(7);
        System.out.println("atk: " + atk);
        try {
            likesService.addLike(likesDTO, atk);
        } catch (IllegalArgumentException e) {
            // 좋아요 추가 실패 시 에러 처리
            log.error("좋아요 추가 실패:", e);
            // 추가적인 예외 처리 로직을 작성하거나 에러 메시지를 클라이언트로 전달할 수 있습니다.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "좋아요 추가 실패");
        }
    }

    // 좋아요 취소 컨트롤러
    @DeleteMapping("/removeLike/{qno}")
    public void removeLike(@RequestHeader("Authorization") String data, @PathVariable Long qno, @RequestBody LikesDTO likesDTO) {
        log.info("좋아요 취소 컨트롤러 진입");
        String atk = data.substring(7);

        likesDTO.setQno(qno); // 좋아요 취소할 질문 번호 설정

        likesService.removeLike(likesDTO, atk);
    }
}