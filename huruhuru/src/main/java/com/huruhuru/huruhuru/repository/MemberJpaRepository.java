package com.huruhuru.huruhuru.repository;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    @Query("SELECT m FROM MemberEntity m JOIN m.scoreList s " +
            "GROUP BY m.id " +
            "ORDER BY SUM(s.bestScore) DESC, m.testCount DESC limit 10")
    List<MemberEntity> findTop10MembersOrderByBestScoreSumAndTestCount();

    @Query("SELECT m FROM MemberEntity m JOIN m.scoreList s " +
            "GROUP BY m.id " +
            "ORDER BY SUM(s.bestScore) DESC, m.testCount DESC")
    List<MemberEntity> findMembersOrderByBestScoreSumAndTestCount();
}
