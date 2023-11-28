package com.huruhuru.huruhuru.repository;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import com.huruhuru.huruhuru.domain.entity.QuestionEntity;
import com.huruhuru.huruhuru.domain.entity.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreJpaRepository extends JpaRepository<ScoreEntity, Long> {
    Optional<ScoreEntity> findByMemberAndTheme(MemberEntity member, Long theme);
    List<ScoreEntity> findByMemberId(Long memberId);
}
