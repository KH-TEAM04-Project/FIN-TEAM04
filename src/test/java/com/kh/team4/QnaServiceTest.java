package com.kh.team4;

import com.kh.team4.dto.QnaDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.entity.Qna;
import com.kh.team4.repository.MemberRepository;
import com.kh.team4.repository.QnaRepository;
import com.kh.team4.service.QnaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QnaServiceTest {

    @Mock
    private QnaRepository qnaRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("QnaService - register")
    void registerTest() {
        // Given
        Member member = new Member();
        QnaDTO qnaDTO = QnaDTO.builder()
                .title("Test Title")
                .content("Test Content")
                .build();

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        // When
        QnaService qnaService = new QnaService(qnaRepository, memberRepository);
        qnaService.register(qnaDTO);

        // Then
        verify(memberRepository, times(1)).findById(anyLong());
        verify(qnaRepository, times(1)).save(any(Qna.class));
        verify(memberRepository).findById(Mockito.anyLong());
    }
}