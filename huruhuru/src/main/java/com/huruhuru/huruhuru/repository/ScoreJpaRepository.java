package com.huruhuru.huruhuru.repository;

import com.huruhuru.huruhuru.domain.entity.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreJpaRepository extends JpaRepository<ScoreEntity, Long> {
}
