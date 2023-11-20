package com.huruhuru.huruhuru.service;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import com.huruhuru.huruhuru.domain.entity.QuestionEntity;
import com.huruhuru.huruhuru.domain.entity.ScoreEntity;
import com.huruhuru.huruhuru.repository.MemberJpaRepository;
import com.huruhuru.huruhuru.repository.QuestionJpaRepository;
import com.huruhuru.huruhuru.repository.ScoreJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionJpaRepository questionJpaRepository;

    private final MemberJpaRepository memberJpaRepository;

    private final ScoreJpaRepository scoreJpaRepository;

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

    public List<QuestionEntity> getQuestionsByCategoryAndTheme(Long category, Long theme) {
        return questionJpaRepository.findByCategoryAndTheme(category, theme);
    }

    public void saveQuestionAndScore(Long memberId, Long theme, Long score) {
        // 1. 현재 로그인한 회원 조회
        MemberEntity member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        // 2. 현재 테마 대한 기존의 점수 조회
        Optional<ScoreEntity> existingScore = scoreJpaRepository.findByMemberAndTheme(member, theme);

        if (existingScore.isPresent()) {
            // 3. 기존의 점수가 있으면 더 큰 경우에만 업데이트
            ScoreEntity currentScore = existingScore.get();
            if (score > currentScore.getBestScore()) {
                currentScore.saveBestScore(score);
                scoreJpaRepository.save(currentScore);
            }
        } else {
            // 4. 기존의 점수가 없으면 새로운 점수 엔터티 생성 및 저장
            ScoreEntity newScore = ScoreEntity.builder()
                    .member(member)
                    .theme(theme)
                    .bestScore(score)
                    .build();
            scoreJpaRepository.save(newScore);
        }
    }

}
