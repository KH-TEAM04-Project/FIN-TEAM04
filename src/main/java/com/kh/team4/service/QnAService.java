package com.kh.team4.service;

import com.kh.team4.dto.QnADTO;
import com.kh.team4.entity.QnA;
import com.kh.team4.repository.FilesRepository;
import com.kh.team4.repository.QnARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class QnAService {
    private final QnARepository qnaRepository;

    public void save(QnADTO qnaDTO) throws IOException {

    }
}
