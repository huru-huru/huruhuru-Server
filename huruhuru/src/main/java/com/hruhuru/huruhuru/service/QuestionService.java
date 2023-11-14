package com.hruhuru.huruhuru.service;

import com.hruhuru.huruhuru.repository.QuestionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionJpaRepository questionJpaRepository;

    public Long getTotalTestCount() {
        return questionJpaRepository.getTotalTestCount().orElse(0L);
    }
}
