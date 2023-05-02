package com.kh.team4.service;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.QnaDTO;
import com.kh.team4.entity.Qna;
import com.kh.team4.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;

    public void save(QnaDTO qnaDTO) throws IOException {
        Qna qna = Qna.dtoToEntity(qnaDTO);
        qnaRepository.save(qna);
    }

    @Transactional
    public List<QnaDTO> findAll() {
      List<Qna> qnaList = qnaRepository.findAll();

      List<QnaDTO> qnaDTOList = new ArrayList<>();
      for (Qna qna:qnaList) {
          qnaDTOList.add(QnaDTO.toQnaDTO(qna));
      }
      return qnaDTOList;
    }
}
