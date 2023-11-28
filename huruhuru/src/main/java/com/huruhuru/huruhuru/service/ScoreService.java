package com.huruhuru.huruhuru.service;

import com.huruhuru.huruhuru.domain.entity.ScoreEntity;
import com.huruhuru.huruhuru.dto.ScoreDto;
import com.huruhuru.huruhuru.repository.ScoreJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    private final ScoreJpaRepository scoreRepository;

    @Autowired
    public ScoreService(ScoreJpaRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }
    public List<ScoreEntity> getScoresByMemberId(Long memberId) {
        return scoreRepository.findByMemberId(memberId);
    }
    public ScoreDto convertToDto(ScoreEntity scoreEntity) {
        ScoreDto scoreDto = new ScoreDto();
        scoreDto.setTheme(scoreEntity.getTheme());
        scoreDto.setBestScore(scoreEntity.getBestScore());
        return scoreDto;
    }
}
