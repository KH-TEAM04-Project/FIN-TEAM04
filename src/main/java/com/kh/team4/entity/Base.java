package com.kh.team4.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter

public class Base {
    @CreationTimestamp // 생성시간
    @Column(updatable = false)  // 수정시 관여 x
    private LocalDateTime regDate;

    @UpdateTimestamp    // 업데이트 시간
    @Column(insertable = false) // 입력시 관여 x
    private LocalDateTime modDate;
}
