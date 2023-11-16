package com.huruhuru.huruhuru.domain.entity;

import com.huruhuru.huruhuru.domain.BaseTimeEntity;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    private Long theme;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long bestScore;
}
