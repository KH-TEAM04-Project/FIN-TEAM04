package com.kh.team4.dto;

import com.kh.team4.entity.Reply;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ReplyDTO {
    private Long rno;
    private String content;
    private LocalDateTime regDate;

}
