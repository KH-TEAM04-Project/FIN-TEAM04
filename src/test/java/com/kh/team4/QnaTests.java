package com.kh.team4;

import com.kh.team4.dto.QnaDTO;
import com.kh.team4.service.QnaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class QnaTests {
    @Autowired
    QnaService qnaService;

    @Test
    void save() throws IOException {

    QnaDTO qnaDTO = QnaDTO.builder()
            .title("34ㅅㅈㄷㅅㄷㄱ5345")
            .content("ㅅㄱㄷㄱㄷㄳㄱㄷㅅㄱㄷㅅㄷㄳㄷ")
            .secret(1)
            .hits(1)
            .build();

            qnaService.save(qnaDTO);
    }
}
