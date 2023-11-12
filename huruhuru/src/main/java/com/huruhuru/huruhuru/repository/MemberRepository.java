package com.huruhuru.huruhuru.repository;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    MemberEntity findByNickname(String nickname);
}
