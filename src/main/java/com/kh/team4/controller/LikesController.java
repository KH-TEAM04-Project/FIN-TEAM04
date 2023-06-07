package com.kh.team4.controller;

import com.kh.team4.dto.LikesDTO;
import com.kh.team4.service.LikesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    // 좋아요 컨트롤러
    @PostMapping("/likes/{qno}")
    public void addLike(@RequestBody LikesDTO likesDTO) {
        log.info("좋아요 컨트롤러 진입");
        likesService.likes(likesDTO);
    }
    // 좋아요 취소 컨트롤러
    @PostMapping("/unLikes/{qno}")
    public void removeLike(@RequestBody LikesDTO likesDTO) {
        log.info("좋아요 취소 컨트롤러 진입");
        likesService.unLikes(likesDTO);
    }
}
