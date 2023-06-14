package com.kh.team4.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@ToString
@Getter
@Builder
@AllArgsConstructor
@DynamicInsert //Insert시 Null인 필드를 제외하기위해 사용
@DynamicUpdate  //Update시 Null인 필드를 제외하기위해 사용

public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lno;
    private Long qno; // 게시글 ID
    private Long mno; // 사용자 ID
    protected Likes() {
    }

    private Likes(Long qno, Long mno) {
        this.qno = qno;
        this.mno = mno;
    }
    public static LikesBuilder builder() {
        return new LikesBuilder();
    }
    public static class LikesBuilder {
        private Long qno;
        private Long mno;
        public LikesBuilder qno(Long qno) {
            this.qno = qno;
            return this;
        }
        public LikesBuilder mno(Long mno) {
            this.mno = mno;
            return this;
        }
        public Likes build() {
            return new Likes(qno, mno);
        }
    }
}
