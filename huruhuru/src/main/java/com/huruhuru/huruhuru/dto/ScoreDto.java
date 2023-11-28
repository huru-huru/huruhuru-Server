package com.huruhuru.huruhuru.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ScoreDto {

    private Long theme;
    private Long bestScore;

    public ScoreDto() {
        // 기본 생성자
    }

    public ScoreDto(Long theme, Long bestScore) {
        this.theme = theme;
        this.bestScore = bestScore;
    }

}
