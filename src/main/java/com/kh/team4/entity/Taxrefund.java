package com.kh.team4.entity;


import com.kh.team4.dto.TaxrefundDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor // (@NoArgsConstructor 이거 쓰면서 @Builder 도 동시에 쓰기 위해 사용)
@Table(name = "Taxrefund")
@Builder

@SequenceGenerator(
        name = "TAXREFUND_SEQ_GENERATOR"  //시퀀스 제너레이터 이름
        , sequenceName = "TAXREFUND_SEQ"  //시퀀스 이름
        , initialValue = 1  //시작값
        , allocationSize = 1  //메모리를 통해 할당할 범위 사이즈
)
public class Taxrefund extends Base{
    @Id
    @GeneratedValue(  // 기본키를 자동으로 생성해주는 어노테이션
            strategy = GenerationType.SEQUENCE  //사용할 전략을 시퀀스로 선택
            , generator = "TAXREFUND_SEQ_GENERATOR"
    )
    private Long tno;

    // @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_mno")
    private Member mno; // 회원번호


    @Column
    private String year; //연도

    @Column//(columnDefinition = "long default 0")
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


}
