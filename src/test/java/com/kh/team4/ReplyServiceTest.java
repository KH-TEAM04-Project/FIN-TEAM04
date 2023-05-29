package com.kh.team4;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.kh.team4.dto.ReplyDTO;
import com.kh.team4.entity.Qna;
import com.kh.team4.entity.Reply;
import com.kh.team4.repository.QnaRepository;
import com.kh.team4.repository.ReplyRepository;
import com.kh.team4.service.ReplyService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ReplyServiceTest {


    @Mock
    private ReplyRepository replyRepository;

    @Mock
    private QnaRepository qnaRepository;

    @InjectMocks
    private ReplyService replyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_ValidReply() {
        // Given
        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(1L)
                .content("This is a valid reply.")
                .qno(1L)
                .build();

        Qna qna = Qna.builder()
                .qno(1L)
                .build();

        Reply savedReply = Reply.builder()
                .rno(1L)
                .content(replyDTO.getContent())
                .build();

        when(qnaRepository.findById(1L)).thenReturn(Optional.of(qna));
        when(replyRepository.save(any(Reply.class))).thenReturn(savedReply);

        // When
        Long savedRno = replyService.save(replyDTO);

        // Then
        assertNotNull(savedRno);
        assertEquals(savedReply.getRno(), savedRno);
        verify(qnaRepository, times(1)).findById(1L);
        verify(replyRepository, times(1)).save(any(Reply.class));
    }

    @Test
    void testSave_InvalidReply() {
        // Given
        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(1L)
                .content("This is an invalid reply.")
                .qno(1L)
                .build();

        when(qnaRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Long savedRno = replyService.save(replyDTO);

        // Then
        assertNull(savedRno);
        verify(qnaRepository, times(1)).findById(1L);
        verify(replyRepository, never()).save(any(Reply.class));
    }
    @Test
    void testFindAll() {
        // Given
        Long qnaQno = 1L;

        Qna qna = Qna.builder()
                .qno(qnaQno)
                .build();

        List<Reply> replyList = new ArrayList<>();
        Reply reply1 = Reply.builder()
                .rno(1L)
                .content("First reply")
                .build();
        Reply reply2 = Reply.builder()
                .rno(2L)
                .content("Second reply")
                .build();
        replyList.add(reply1);
        replyList.add(reply2);

        when(qnaRepository.findById(qnaQno)).thenReturn(Optional.of(qna));
        when(replyRepository.findAllByQnaOrderByRnoDesc(qna)).thenReturn(replyList);

        // When
        List<ReplyDTO> replyDTOList = replyService.findAll(qnaQno);

        // Then
        assertEquals(replyList.size(), replyDTOList.size());
        assertEquals(reply1.getRno(), replyDTOList.get(0).getRno());
        assertEquals(reply1.getContent(), replyDTOList.get(0).getContent());
        assertEquals(reply2.getRno(), replyDTOList.get(1).getRno());
        assertEquals(reply2.getContent(), replyDTOList.get(1).getContent());
        verify(qnaRepository, times(1)).findById(qnaQno);
        verify(replyRepository, times(1)).findAllByQnaOrderByRnoDesc(qna);
    }


}