package com.huruhuru.huruhuru.repository;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    boolean existsByNickname(String nickname);
    Optional<MemberEntity> findByNickname(String nickname);



    @Query("SELECT m FROM MemberEntity m LEFT JOIN m.scoreList s " +
            "GROUP BY m.id " +
            "ORDER BY COALESCE(SUM(s.bestScore), 0) DESC, m.testCount DESC")
    List<MemberEntity> findMembersOrderByBestScoreSumAndTestCount();

    @Query("SELECT m FROM MemberEntity m ORDER BY m.totalBestScore DESC, m.testCount DESC limit 10")
    List<MemberEntity> findMembersOrderByBestScoreSumAndTestCountRank();

//    @Query("SELECT m FROM MemberEntity m JOIN m.scoreList s " +
//            "GROUP BY m.id " +
//            "ORDER BY SUM(s.bestScore) DESC, m.testCount DESC")
//    List<MemberEntity> findTop10MembersOrderByBestScoreSumAndTestCount();

}
