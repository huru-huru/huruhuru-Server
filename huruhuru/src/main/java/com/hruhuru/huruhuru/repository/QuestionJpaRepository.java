package com.hruhuru.huruhuru.repository;

import com.hruhuru.huruhuru.domain.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestionJpaRepository extends JpaRepository<QuestionEntity, Long> {

    @Query("SELECT SUM(q.testCount) FROM QuestionEntity q")
    Optional<Long> getTotalTestCount();
}
