package com.kh.team4.dto;

import com.kh.team4.embeded.Cardbene;
import com.kh.team4.entity.Cardinfo;
import com.kh.team4.entity.Dcard;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardinfoDTO {

    private Long cardno;
    private String cname;
    private String bname;
    private String imgadd;

    private Float allfor;
    private Float airport;
    private Float edu;
    private Float tran;
    private Float cultural;
    private Float delivery;
    private Float stores;
    private Float giftcard;
    private Float food;
    private Float shop;
    private Float post;
    private Float medi;
    private Float cloth;
    private Float commi;
    private Float travel;
    private Float gas;
    private Float coffee;
    private Float commu;
    private Float conveni;
    private Float overseas;
    private Float foreign;
    private Float hotel;
    private Float publicfee;

    public static CardinfoDTO entityToDTO(Optional<Cardinfo> cardinfo) {
        return CardinfoDTO.builder()
                .cardno(cardinfo.get().getCardno())
                .cname(cardinfo.get().getCname())
                .bname(cardinfo.get().getBname())
                .imgadd(cardinfo.get().getImgadd())
                .allfor(Float.parseFloat(cardinfo.get().getCardbenefit().getAllfor()))
                .airport(Float.parseFloat(cardinfo.get().getCardbenefit().getAirport()))
                .cloth(Float.parseFloat(cardinfo.get().getCardbenefit().getCloth()))
                .coffee(Float.parseFloat(cardinfo.get().getCardbenefit().getCoffee()))
                .commi(Float.parseFloat(cardinfo.get().getCardbenefit().getCommi()))
                .commu(Float.parseFloat(cardinfo.get().getCardbenefit().getCommu()))
                .conveni(Float.parseFloat(cardinfo.get().getCardbenefit().getConveni()))
                .cultural(Float.parseFloat(cardinfo.get().getCardbenefit().getCultural()))
                .gas(Float.parseFloat(cardinfo.get().getCardbenefit().getGas()))
                .delivery(Float.parseFloat(cardinfo.get().getCardbenefit().getDelivery()))
                .edu(Float.parseFloat(cardinfo.get().getCardbenefit().getEdu()))
                .foreign(Float.parseFloat(cardinfo.get().getCardbenefit().getForeign()))
                .shop(Float.parseFloat(cardinfo.get().getCardbenefit().getShop()))
                .tran(Float.parseFloat(cardinfo.get().getCardbenefit().getTran()))
                .medi(Float.parseFloat(cardinfo.get().getCardbenefit().getMedi()))
                .travel(Float.parseFloat(cardinfo.get().getCardbenefit().getTravel()))
                .hotel(Float.parseFloat(cardinfo.get().getCardbenefit().getHotel()))
                .stores(Float.parseFloat(cardinfo.get().getCardbenefit().getStores()))
                .build();
    }
}
