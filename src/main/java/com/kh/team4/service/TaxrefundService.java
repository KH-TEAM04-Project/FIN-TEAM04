package com.kh.team4.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.team4.dto.DcardDTO;
import com.kh.team4.dto.TaxrefundDTO;
import com.kh.team4.entity.Dcard;
import com.kh.team4.entity.Member;
import com.kh.team4.jwt.TokenProvider;
import com.kh.team4.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaxrefundService {

    private final TaxrefundRepository repository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final DcardRepository drepository;
    private final CardinfoRepositoryImpl crepository;


    public TaxrefundDTO detail(String atk) {
        System.out.println("Detail Service 진입");
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        System.out.println("전달받은 값1 " + mno);
        Member mno2 = new Member(mno);
        System.out.println("전달받은 값2 " + mno2);
        TaxrefundDTO dto = TaxrefundDTO.entityToDTO(repository.findByMno(mno2));
        System.out.println("연말정산 페이지 들어가여: " + dto.toString());
        return dto;
    }

    public String checkDetail(String atk) {
        System.out.println("체크카드 항목 한번 보자");
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        System.out.println("전달받은 값1 " + mno.toString());
        Member mno2 = new Member(mno);
        System.out.println("전달받은 값2 " + mno2);
        Optional<Dcard> dcard = drepository.findByMno(mno2);
        DcardDTO dto = DcardDTO.entityToDTO(dcard);
        System.out.println(mno + "님의 체크카드 항목은? " + dto);

        System.out.println("top3 " + dto.toString());

        Long maxvalue = dto.findMaxValue();
        System.out.println("최대값: " + maxvalue);
        String maxvalueColumnName = dto.findMaxValueColumnName();
        System.out.println("최대값 컬럼명" + maxvalueColumnName);

        Long midvalue = dto.find2Value();
        System.out.println("그다음: " + midvalue);
        String midvalueColumnName = dto.find2ValueColumnName();
        System.out.println("그다음 컬럼명" + midvalueColumnName);

        Long thrdvalue = dto.find3Value();
        System.out.println("마지막: " + thrdvalue);
        String thrdvalueColumnName = dto.find3ValueColumnName();
        System.out.println("마지막 컬럼명" + thrdvalueColumnName);

        Map<String, Long> valueMap = new HashMap<>();
        valueMap.put(maxvalueColumnName, maxvalue);
        valueMap.put(midvalueColumnName, midvalue);
        valueMap.put(thrdvalueColumnName, thrdvalue);

        for (Map.Entry<String, Long> entry : valueMap.entrySet()) {
            String columnName = entry.getKey();
            Long columnValue = entry.getValue();
            System.out.println("Column Name: " + entry.getKey() + ", Value: " + entry.getValue());
        }

        Float[] aaa = new Float[100];
        for (Long cardno = 1L; cardno < 11L; cardno++) {
            System.out.println("cardno, maxvalueColumnName 값: " + cardno + maxvalueColumnName);
            Float top1 = Float.valueOf(crepository.top1(maxvalueColumnName, cardno).toString());
            System.out.println(top1);
            Float top1v = (float) 0;
            if (top1 > 1) {
                top1v = top1 * 12;
            } else {
                top1v = top1 * maxvalue;
            }
            System.out.println(top1v);

            System.out.println("cardno, midvalueColumnName 값: " + cardno + midvalueColumnName);
            Float top2 = Float.valueOf(crepository.top1(midvalueColumnName, cardno).toString());
            System.out.println(top2);
            Float top2v = (float) 0;
            ;
            if (top2 > 1) {
                top2v = top2 * 12;
            } else {
                top2v = top2 * midvalue;
            }
            System.out.println(top2v);

            System.out.println("cardno, thrdvalueColumnName 값: " + cardno + thrdvalueColumnName);
            Float top3 = Float.valueOf(crepository.top3(thrdvalueColumnName, cardno).toString());
            System.out.println(top3);
            Float top3v = (float) 0;
            ;
            if (top3 > 1) {
                top3v = top3 * 12;
            } else {
                top3v = top3 * thrdvalue;
            }
            System.out.println(top3v);

            List<Float> cardBenefits = new ArrayList<>();
            cardBenefits.add(top1v + top2v + top3);

            aaa[cardno.intValue()] = top1v + top2v + top3v;
            System.out.println(cardno + "의 카드 혜택 3개 더한 값은?" + cardBenefits);
        }

        for (int i = 0; i < aaa.length; i++) {
            if (aaa[i] == null) {
                aaa[i] = 0f;
            }
        }

        System.out.println(Arrays.toString(aaa));

        int cno1 = -1;
        int cno2 = -1;
        int cno3 = -1;

        for (int i = 0; i < aaa.length; i++) {
            Float cardBenefits = aaa[i];

            if (cno1 == -1 || cardBenefits > aaa[cno1]) {
                cno3 = cno2;
                cno2 = cno1;
                cno1 = i;
            } else if (cno2 == -1 || cardBenefits > aaa[cno2]) {
                cno3 = cno2;
                cno2 = i;
            } else if (cno3 == -1 || cardBenefits > aaa[cno3]) {
                cno3 = i;
            }
        }

        System.out.println("cno1: " + cno1);
        System.out.println("cno2: " + cno2);
        System.out.println("cno3: " + cno3);

        String zaolee1 = crepository.findByCno((long) cno1).toString();
        String zaolee2 = crepository.findByCno((long) cno2).toString();
        String zaolee3 = crepository.findByCno((long) cno3).toString();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> recommad = new HashMap<>();
            recommad.put("data1", zaolee1);
            recommad.put("data2", zaolee2);
            recommad.put("data3", zaolee3);
            String json = objectMapper.writeValueAsString(recommad);
            System.out.println(json);
            return String.valueOf(Optional.of(json));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return String.valueOf(Optional.empty());
        }
    }

}

