package com.kh.team4.embeded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable

public class Cardbene {
    private String allfor;
    private String airport;
    private String edu;
    private String tran;
    private String cultural;
    private String delivery;
    private String stores;
    private String giftcard;
    private String food;
    private String shop;
    private String post;
    private String medi;
    private String cloth;
    private String commi;
    private String travel;
    private String gas;
    private String coffee;
    private String commu;
    private String conveni;
    private String overseas;
    private String foreign;
    private String hotel;
    // public 하면 에러남...
    private String publicfee;

}
