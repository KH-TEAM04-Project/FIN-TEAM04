package com.kh.team4.service;

import com.kh.team4.dto.CardinfoDTO;
import com.kh.team4.dto.DcardDTO;
import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.dto.TaxrefundDTO;
import com.kh.team4.entity.Cardinfo;
import com.kh.team4.entity.Dcard;
import com.kh.team4.entity.Member;
import com.kh.team4.jwt.TokenProvider;
import com.kh.team4.repository.CardinfoRepository;
import com.kh.team4.repository.DcardRepository;
import com.kh.team4.repository.MemberRepository;
import com.kh.team4.repository.TaxrefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    private final CardinfoRepository crepository;


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

    public DcardDTO checkDetail(String atk) { // 저기 dto안에 카드 상세 컬럼별로 dto만든 dto 넣어줘야함, 아직 미생성이라 대체해서 넣음
        System.out.println("체크카드 항목 한번 보자");
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = memberRepository.findByMid2(authentication.getName());
        System.out.println("전달받은 값1 " + mno.toString());
        Member mno2 = new Member(mno);
        System.out.println("전달받은 값2 " + mno2);
        Optional<Dcard> dcard = drepository.findByMno(mno2);
        DcardDTO dto = DcardDTO.entityToDTO(dcard);
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
            System.out.println("Column Name: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        List recommand3;
        for (Long i = 1L; i < 2L; i++) {
            List cardinfo = crepository.recommand2(maxvalueColumnName,midvalueColumnName,thrdvalueColumnName,i);
            System.out.println("cardinfo: " + cardinfo);



        }


        System.out.println(mno + "님의 체크카드 항목은? " + dto.toString());
        return dto;
    }
}
