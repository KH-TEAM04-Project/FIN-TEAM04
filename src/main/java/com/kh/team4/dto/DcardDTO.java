package com.kh.team4.dto;

import com.kh.team4.entity.Dcard;
import com.kh.team4.entity.Member;
import com.kh.team4.entity.Taxrefund;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DcardDTO {
    private Long cno;
    private Long mno; // 회원번호
    private String allfor;
    private Long airport;
    private Long edu;
    private Long tran;
    private Long cultural;
    private Long delivery;
    private Long stores;
    private Long giftcard;
    private Long food;
    private Long shop;
    private Long post;
    private Long medi;
    private Long cloth;
    private Long commi;
    private Long travel;
    private Long gas;
    private Long coffee;
    private Long commu;
    private Long conveni;
    private Long overseas;
    private Long foreign;
    private Long hotel;
    private Long publicfee;

    public static DcardDTO entityToDTO(Optional<Dcard> dcard) {
        return DcardDTO.builder()
                .cno(dcard.get().getCno())
                .airport(dcard.get().getAirport())
                .cloth(dcard.get().getCloth())
                .coffee(dcard.get().getCoffee())
                .commi(dcard.get().getCommi())
                .commu(dcard.get().getCommu())
                .conveni(dcard.get().getConveni())
                .cultural(dcard.get().getCultural())
                .gas(dcard.get().getGas())
                .delivery(dcard.get().getDelivery())
                .edu(dcard.get().getEdu())
                .foreign(dcard.get().getForeign())
                .publicfee(dcard.get().getPublicfee())
                .food(dcard.get().getFood())
                .giftcard(dcard.get().getGiftcard())
                .mno(dcard.get().getMno().getMno())
                .allfor(dcard.get().getAllfor())
                .overseas(dcard.get().getOverseas())
                .post(dcard.get().getPost())
                .shop(dcard.get().getShop())
                .tran(dcard.get().getTran())
                .medi(dcard.get().getMedi())
                .travel(dcard.get().getTravel())
                .hotel(dcard.get().getHotel())
                .stores(dcard.get().getStores())
                .build();
    }

    public Long findMaxValue() {
        Long maxValue = null;

        if (airport != null && (maxValue == null || airport.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = airport;
        }
        if (edu != null && (maxValue == null || edu.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = edu;
        }
        if (tran != null && (maxValue == null || tran.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = tran;
        }
        if (cultural != null && (maxValue == null || cultural.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = cultural;
        }
        if (delivery != null && (maxValue == null || delivery.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = delivery;
        }
        if (stores != null && (maxValue == null || stores.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = stores;
        }
        if (giftcard != null && (maxValue == null || giftcard.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = giftcard;
        }
        if (food != null && (maxValue == null || food.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = food;
        }
        if (shop != null && (maxValue == null || shop.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = shop;
        }
        if (post != null && (maxValue == null || post.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = post;
        }
        if (medi != null && (maxValue == null || medi.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = medi;
        }
        if (cloth != null && (maxValue == null || cloth.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = cloth;
        }
        if (commi != null && (maxValue == null || commi.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = commi;
        }
        if (travel != null && (maxValue == null || travel.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = travel;
        }
        if (gas != null && (maxValue == null || gas.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = gas;
        }
        if (coffee != null && (maxValue == null || coffee.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = coffee;
        }
        if (commu != null && (maxValue == null || commu.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = commu;
        }
        if (conveni != null && (maxValue == null || conveni.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = conveni;
        }
        if (overseas != null && (maxValue == null || overseas.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = overseas;
        }
        if (foreign != null && (maxValue == null || foreign.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = foreign;
        }
        if (hotel != null && (maxValue == null || hotel.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = hotel;
        }
        if (publicfee != null && (maxValue == null || publicfee.compareTo(Long.valueOf(maxValue)) > 0)) {
            maxValue = publicfee;
        }
        return maxValue;
    }
    public Long find2Value() {
        Long maxValue = findMaxValue();
        Long midValue = null;

        if (airport != null && !airport.equals(maxValue) && (midValue == null || airport.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = airport;
        }
        if (edu != null && !edu.equals(maxValue) && (midValue == null || edu.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = edu;
        }
        if (tran != null && !tran.equals(maxValue) && (midValue == null || tran.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = tran;
        }
        if (cultural != null && !cultural.equals(maxValue) && (midValue == null || cultural.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = cultural;
        }
        if (delivery != null && !delivery.equals(maxValue)  && (midValue == null || delivery.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = delivery;
        }
        if (stores != null && !stores.equals(maxValue)  && (midValue == null || stores.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = stores;
        }
        if (giftcard != null && !giftcard.equals(maxValue)  && (midValue == null || giftcard.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = giftcard;
        }
        if (food != null && !food.equals(maxValue)  && (midValue == null || food.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = food;
        }
        if (shop != null && !shop.equals(maxValue)  && (midValue == null || shop.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = shop;
        }
        if (post != null && !post.equals(maxValue)  && (midValue == null || post.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = post;
        }
        if (medi != null && !medi.equals(maxValue)  && (midValue == null || medi.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = medi;
        }
        if (cloth != null && !cloth.equals(maxValue)  && (midValue == null || cloth.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = cloth;
        }
        if (commi != null && !commi.equals(maxValue)  && (midValue == null || commi.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = commi;
        }
        if (travel != null && !travel.equals(maxValue)  && (midValue == null || travel.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = travel;
        }
        if (gas != null && !gas.equals(maxValue)  && (midValue == null || gas.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = gas;
        }
        if (coffee != null && !coffee.equals(maxValue)  && (midValue == null || coffee.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = coffee;
        }
        if (commu != null && !commu.equals(maxValue)  && (midValue == null || commu.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = commu;
        }
        if (conveni != null && !conveni.equals(maxValue)  && (midValue == null || conveni.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = conveni;
        }
        if (overseas != null && !overseas.equals(maxValue)  && (midValue == null || overseas.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = overseas;
        }
        if (foreign != null && !foreign.equals(maxValue)  && (midValue == null || foreign.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = foreign;
        }
        if (hotel != null && !hotel.equals(maxValue)  && (midValue == null || hotel.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = hotel;
        }
        if (publicfee != null && !publicfee.equals(maxValue)  && (midValue == null || publicfee.compareTo(Long.valueOf(midValue)) > 0)) {
            midValue = publicfee;
        }
        return midValue;
    }

    public Long find3Value() {
        Long maxValue = findMaxValue();
        Long midValue = find2Value();
        Long thrdValue = null;

        if (airport != null && !airport.equals(maxValue) && !airport.equals(midValue) && (thrdValue == null || airport.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = airport;
        }
        if (edu != null && !edu.equals(maxValue) && !edu.equals(midValue)  && (thrdValue == null || edu.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = edu;
        }
        if (tran != null && !tran.equals(maxValue) && !tran.equals(midValue)  && (thrdValue == null || tran.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = tran;
        }
        if (cultural != null && !cultural.equals(maxValue) && !cultural.equals(midValue)  && (thrdValue == null || cultural.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = cultural;
        }
        if (delivery != null && !delivery.equals(maxValue) && !delivery.equals(midValue)  && (thrdValue == null || delivery.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = delivery;
        }
        if (stores != null && !stores.equals(maxValue) && !stores.equals(midValue)  && (thrdValue == null || stores.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = stores;
        }
        if (giftcard != null && !giftcard.equals(maxValue) && !giftcard.equals(midValue)  && (thrdValue == null || giftcard.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = giftcard;
        }
        if (food != null && !food.equals(maxValue) && !food.equals(midValue)  && (thrdValue == null || food.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = food;
        }
        if (shop != null && !shop.equals(maxValue) && !shop.equals(midValue)  && (thrdValue == null || shop.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = shop;
        }
        if (post != null && !post.equals(maxValue) && !post.equals(midValue)  && (thrdValue == null || post.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = post;
        }
        if (medi != null && !medi.equals(maxValue) && !medi.equals(midValue)  && (thrdValue == null || medi.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = medi;
        }
        if (cloth != null && !cloth.equals(maxValue) && !cloth.equals(midValue)  && (thrdValue == null || cloth.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = cloth;
        }
        if (commi != null && !commi.equals(maxValue) && !commi.equals(midValue)  && (thrdValue == null || commi.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = commi;
        }
        if (travel != null && !travel.equals(maxValue) && !travel.equals(midValue)  && (thrdValue == null || travel.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = travel;
        }
        if (gas != null && !gas.equals(maxValue) && !gas.equals(midValue)  && (thrdValue == null || gas.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = gas;
        }
        if (coffee != null && !coffee.equals(maxValue) && !coffee.equals(midValue)  && (thrdValue == null || coffee.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = coffee;
        }
        if (commu != null && !commu.equals(maxValue) && !commu.equals(midValue)  && (thrdValue == null || commu.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = commu;
        }
        if (conveni != null && !conveni.equals(maxValue) && !conveni.equals(midValue)  && (thrdValue == null || conveni.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = conveni;
        }
        if (overseas != null && !overseas.equals(maxValue) && !overseas.equals(midValue)  && (thrdValue == null || overseas.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = overseas;
        }
        if (foreign != null && !foreign.equals(maxValue) && !foreign.equals(midValue)  && (thrdValue == null || foreign.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = foreign;
        }
        if (hotel != null && !hotel.equals(maxValue) && !hotel.equals(midValue)  && (thrdValue == null || hotel.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = hotel;
        }
        if (publicfee != null && !publicfee.equals(maxValue) && !publicfee.equals(midValue)  && (thrdValue == null || publicfee.compareTo(Long.valueOf(thrdValue)) > 0)) {
            thrdValue = publicfee;
        }
        return thrdValue;
    }

    public String findMaxValueColumnName() {
        Map<String, Long> columnMap = new HashMap<>();
        columnMap.put("airport", getAirport());
        columnMap.put("cloth", getCloth());
        columnMap.put("coffee", getCoffee());
        columnMap.put("commi", getCommi());
        columnMap.put("commu", getCommu());
        columnMap.put("conveni", getConveni());
        columnMap.put("cultural", getCultural());
        columnMap.put("gas", getGas());
        columnMap.put("delivery", getDelivery());
        columnMap.put("edu", getEdu());
        columnMap.put("foreign", getForeign());
        columnMap.put("publicfee", getPublicfee());
        columnMap.put("food", getFood());
        columnMap.put("giftcard", getGiftcard());
        columnMap.put("overseas", getOverseas());
        columnMap.put("post", getPost());
        columnMap.put("shop", getShop());
        columnMap.put("tran", getTran());
        columnMap.put("medi", getMedi());
        columnMap.put("travel", getTravel());
        columnMap.put("hotel", getHotel());
        columnMap.put("stores", getStores());
        String columnName1 = null;
        for (Map.Entry<String, Long> entry : columnMap.entrySet()) {
            if (entry.getValue().equals(findMaxValue())) {
                columnName1 = entry.getKey();
                break;
            }
        }
        return columnName1;
    }

    public String find2ValueColumnName() {
        Map<String, Long> columnMap = new HashMap<>();
        columnMap.put("airport", getAirport());
        columnMap.put("cloth", getCloth());
        columnMap.put("coffee", getCoffee());
        columnMap.put("commi", getCommi());
        columnMap.put("commu", getCommu());
        columnMap.put("conveni", getConveni());
        columnMap.put("cultural", getCultural());
        columnMap.put("gas", getGas());
        columnMap.put("delivery", getDelivery());
        columnMap.put("edu", getEdu());
        columnMap.put("foreign", getForeign());
        columnMap.put("publicfee", getPublicfee());
        columnMap.put("food", getFood());
        columnMap.put("giftcard", getGiftcard());
        columnMap.put("overseas", getOverseas());
        columnMap.put("post", getPost());
        columnMap.put("shop", getShop());
        columnMap.put("tran", getTran());
        columnMap.put("medi", getMedi());
        columnMap.put("travel", getTravel());
        columnMap.put("hotel", getHotel());
        columnMap.put("stores", getStores());
        String columnName2 = null;
        for (Map.Entry<String, Long> entry : columnMap.entrySet()) {
            if (entry.getValue().equals(find2Value())) {
                columnName2 = entry.getKey();
                break;
            }
        }
        return columnName2;
    }
    public String find3ValueColumnName() {
        Map<String, Long> columnMap = new HashMap<>();
        columnMap.put("airport", getAirport());
        columnMap.put("cloth", getCloth());
        columnMap.put("coffee", getCoffee());
        columnMap.put("commi", getCommi());
        columnMap.put("commu", getCommu());
        columnMap.put("conveni", getConveni());
        columnMap.put("cultural", getCultural());
        columnMap.put("gas", getGas());
        columnMap.put("delivery", getDelivery());
        columnMap.put("edu", getEdu());
        columnMap.put("foreign", getForeign());
        columnMap.put("publicfee", getPublicfee());
        columnMap.put("food", getFood());
        columnMap.put("giftcard", getGiftcard());
        columnMap.put("overseas", getOverseas());
        columnMap.put("post", getPost());
        columnMap.put("shop", getShop());
        columnMap.put("tran", getTran());
        columnMap.put("medi", getMedi());
        columnMap.put("travel", getTravel());
        columnMap.put("hotel", getHotel());
        columnMap.put("stores", getStores());
        String columnName3 = null;
        for (Map.Entry<String, Long> entry : columnMap.entrySet()) {
            if (entry.getValue().equals(find3Value())) {
                columnName3 = entry.getKey();
                break;
            }
        }
        return columnName3;
    }





}
