package com.kh.team4.service;

import com.kh.team4.dto.LikesDTO;
import com.kh.team4.entity.Likes;
import com.kh.team4.repository.LikesRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@Getter
@Setter
public class LikesService {
    private final LikesRepository likesRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }
    // 좋아요 기능
    public void likes(LikesDTO likesDTO) {
        log.info("좋아요 서비스 진입 DTO : " + likesDTO);
        Likes likes = Likes.builder()
                .qno(likesDTO.getQno())
                .mno(likesDTO.getMno())
                .build();
        likesRepository.save(likes);
    }
    // 좋아요 취소 기능
    public void unLikes(LikesDTO likesDTO) {
        log.info("좋아요 취소 서비스 진입 DTO: " + likesDTO);
        List<Likes> likesList = likesRepository.findAllByQnoAndMno(likesDTO.getQno(), likesDTO.getMno());
        if (!likesList.isEmpty()) {
            Likes likes = likesList.get(0); // Assuming you only expect one result
            likesRepository.delete(likes);
        }
    }
}
