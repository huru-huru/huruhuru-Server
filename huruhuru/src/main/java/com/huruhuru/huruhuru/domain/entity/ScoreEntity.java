package com.huruhuru.huruhuru.domain.entity;

import com.huruhuru.huruhuru.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "score")
public class ScoreEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    private Long theme;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long bestScore;

    public void saveBestScore(Long bestScore) {
        this.bestScore = bestScore;
    }

    @Builder
    public ScoreEntity(MemberEntity member, Long theme,Long bestScore) {
        this.member = member;
        this.theme = theme;
        this.bestScore = bestScore;
    }

}
