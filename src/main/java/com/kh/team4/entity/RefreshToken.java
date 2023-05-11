package com.kh.team4.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "token")
@Entity
public class RefreshToken {

    @Id
    @Column(name = "tkey")
    private String key;
    // mId 값 들어갈꺼임

    @Column(name = "tvalue")
    private String value;
    // refresh token값 들어갈곳

    @Builder
    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}