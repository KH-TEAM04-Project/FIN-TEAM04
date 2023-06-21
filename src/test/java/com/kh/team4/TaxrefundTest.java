package com.kh.team4;

import com.kh.team4.entity.Member;
import com.kh.team4.entity.Taxrefund;
import com.kh.team4.repository.TaxrefundRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.LongStream;

@SpringBootTest
public class TaxrefundTest {
    @Autowired
    TaxrefundRepository taxrefundRepository;

    @Test
    void save() {

        LongStream.rangeClosed(1, 50).forEach(i -> {
            Taxrefund parms = Taxrefund.builder()
                    .card(1001231311230L)
                    .cash(i)
                    .dcard(9970000L)
                    .donation(i)
                    .edu(i)
                    .mno(new Member(i))
                    .housefunds(i)
                    .housesaving(i)
                    .insurance(i)
                    .invest(i)
                    .lifeinsurance(i)
                    .medi(i)
                    .npension(i)
                    .pension(i)
                    .sbo(i)
                    .build();

            taxrefundRepository.save(parms);
        });
    }
}