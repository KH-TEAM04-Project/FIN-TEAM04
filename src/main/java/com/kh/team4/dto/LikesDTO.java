package com.kh.team4.dto;

import com.kh.team4.entity.Likes;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LikesDTO {
    private Long lno;
    private Long qno;
    private String mno;
    private String writerID;
    private String regDate;
    private LocalDateTime rawRegDate;
    public static LikesDTO entityToDto(Likes likes) {
        LikesDTO likesDTO = new LikesDTO();
        likesDTO.setLno(likes.getLno());
        likesDTO.setQno(likes.getQna().getQno());
        likesDTO.setWriterID(likes.getMember().getMid());
        likesDTO.setRawRegDate(likes.getRegDate());

        // regDate 필드에 형식화된 작성일 설정
        likesDTO.setRegDate(likes.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


        return likesDTO;
    }

    public Long getMno() {
        return mno != null ? Long.parseLong(mno) : null;
    }

}
