package com.kh.team4.entity;

import com.kh.team4.dto.LikesDTO;
import com.kh.team4.dto.ReplyDTO;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Likes extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qno")
    private Qna qna;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_mno")
    private Member member;

    public static Likes dtoToEntity(LikesDTO likesDTO, Qna qna, Member member) {
        Likes likes = new Likes();
        likes.setQna(qna);
        likes.setMember(member);
        return likes;
    }
}
