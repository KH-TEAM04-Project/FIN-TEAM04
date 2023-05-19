package com.kh.team4.embeded;


import javax.persistence.Embeddable;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable     // 병합 기능을 사용할 클래스
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Address {
    // private String numAddress; 우편번호를 사용할 때를 대비
    private String address;
    private String detailAdderess;

}
