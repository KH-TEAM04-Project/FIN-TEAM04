package com.kh.team4.service;

import com.kh.team4.dto.TaxrefundDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.repository.TaxrefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaxrefundService {

    private final TaxrefundRepository repository;


    public TaxrefundDTO detail(Long mno) {
        System.out.println("전달받은 값 " + mno.toString());
        Member mno2 = new Member(mno);
        System.out.println("전달받은 값 " + mno2);
        TaxrefundDTO dto = TaxrefundDTO.entityToDTO(repository.findByMno(mno2));
        System.out.println("연말정산 페이지 들어가여: " + dto.toString());
        return dto;
    }
}
