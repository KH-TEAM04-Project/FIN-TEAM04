package com.kh.team4.dto;
import com.kh.team4.entity.Member;
import com.kh.team4.entity.Taxrefund;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxrefundDTO {

    private Long tno;
    private Member mno;
    private String year; //연도
    private Long lifeinsurance; //건강보험
    private Long npension; //국민연금
    private Long insurance; //보험료
    private Long medi; //의료비
    private Long edu; //교육비
    private Long card; // 신카
    private Long dcard; //체카
    private Long pension; //개인연금저축,연금계좌
    private Long cash; //현금영수증
    private Long housefunds; //주택자금,월세액
    private Long housesaving; //주택마련저축
    private Long invest; //장기집합투자증권저축,벤처기업투자신탁
    private Long sbo; //소기업,소상공인 공제부금
    private Long donation; // 기부금

    public static TaxrefundDTO entityToDTO(Optional<Taxrefund> tax) {
        TaxrefundDTO taxrefund = TaxrefundDTO.builder()
                .card(tax.get().getCard())
                .donation(tax.get().getDonation())
                .edu(tax.get().getEdu())
                .year(tax.get().getYear())
                .dcard(tax.get().getDcard())
                .housefunds(tax.get().getHousefunds())
                .insurance(tax.get().getInsurance())
                .invest(tax.get().getInvest())
                .sbo(tax.get().getSbo())
                .lifeinsurance(tax.get().getLifeinsurance())
                .pension(tax.get().getPension())
                .medi(tax.get().getMedi())
                .npension(tax.get().getNpension())
                .tno(tax.get().getTno())
                .mno(tax.get().getMno())
                .year(tax.get().getYear())
                .tno(tax.get().getTno())
                .build();
        return taxrefund;
    }
}
