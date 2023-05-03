package com.kh.team4;

import com.kh.team4.dto.QnaDTO;
import com.kh.team4.service.QnaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.verify;

public class TestQnaService {

    @Mock
    private QnaService qnaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() throws IOException {
        QnaDTO qnaDTO = QnaDTO.builder()
                .title("title")
                .content("content")
                .secret(1)
                .hits(0)
                .build();

        qnaService.save(qnaDTO);

        verify(qnaService).save(qnaDTO);
    }
}