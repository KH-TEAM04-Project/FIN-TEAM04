package com.kh.team4.dto;

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
    private LocalDateTime regdate;
    private LocalDateTime moddate;
}