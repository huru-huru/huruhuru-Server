package com.huruhuru.huruhuru.repository;

import com.huruhuru.huruhuru.domain.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface QuestionJpaRepository extends JpaRepository<QuestionEntity, Long> {

    // 모든 testCount의 값 합쳐서 반환
    @Query("SELECT SUM(q.testCount) FROM QuestionEntity q")
    Optional<Long> getTotalTestCount();

    // id로 question 찾아서 testCount 1 증가
    @Transactional
    @Modifying
    @Query("UPDATE QuestionEntity q SET q.testCount = q.testCount + 1 WHERE q.category = :category AND q.theme = :theme AND q.questionNumber = 10")
    void plusTotalTestCount(@Param("category") Long category, @Param("theme") Long theme);

    // id로 question 찾아서 testCount 반환
    @Query("SELECT SUM(q.testCount) FROM QuestionEntity q WHERE q.category = :category AND q.theme = :theme")
    Optional<Long> getTestCountByCategoryAndTheme(@Param("category") Long category, @Param("theme") Long theme);

}
