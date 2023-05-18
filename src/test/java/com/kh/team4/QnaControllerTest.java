package com.kh.team4;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Optional;

import com.kh.team4.controller.QnaController;
import com.kh.team4.dto.QnaDTO;
import com.kh.team4.service.QnaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class QnaControllerTest {

    @Mock
    private QnaService qnaService;

    @InjectMocks
    private QnaController qnaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_ExistingQna() {
        Long qno = 1L;
        QnaDTO qnaDTO = new QnaDTO();
        qnaDTO.setQno(qno);

        when(qnaService.findById(qno)).thenReturn(qnaDTO);

        ResponseEntity<Map<String, Object>> response = qnaController.findById(qno);

        verify(qnaService, times(1)).findById(qno);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(qnaDTO, response.getBody());
    }

    @Test
    void testFindById_NonExistingQna() {
        Long qno = 1L;

        when(qnaService.findById(qno)).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = qnaController.findById(qno);

        verify(qnaService, times(1)).findById(qno);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}