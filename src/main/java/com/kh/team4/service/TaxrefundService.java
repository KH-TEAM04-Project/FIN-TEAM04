package com.kh.team4.service;

import com.kh.team4.dto.MemberResDTO;
import com.kh.team4.dto.TaxrefundDTO;
import com.kh.team4.entity.Taxrefund;
import com.kh.team4.repository.TaxrefundRepository;

import java.util.Optional;

public class TaxrefundService {

    TaxrefundRepository repository;

    public TaxrefundDTO detail(Long mno) {
        TaxrefundDTO dto = TaxrefundDTO.entityToDTO(repository.findByMno(mno));
        System.out.println("연말정산 페이지 들어가여: " + dto.toString());
        return dto;
    }
}
