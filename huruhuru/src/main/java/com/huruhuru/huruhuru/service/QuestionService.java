package com.huruhuru.huruhuru.service;

import com.huruhuru.huruhuru.repository.MemberJpaRepository;
import com.huruhuru.huruhuru.repository.QuestionJpaRepository;
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


    // 10번 질문에만 testCount += 1
    public void plusTotalTestCount(Long category, Long theme) {
        questionJpaRepository.plusTotalTestCount(category, theme);
    }


    public Long getTestCountByCategoryAndTheme(Long category, Long theme) {
        return questionJpaRepository.getTestCountByCategoryAndTheme(category, theme).orElse(0L);
    }

}
