package com.huruhuru.huruhuru.service;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import com.huruhuru.huruhuru.dto.response.member.MemberRankingGetResponse;
import com.huruhuru.huruhuru.global.exception.MemberException;
import com.huruhuru.huruhuru.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;

    public List<MemberEntity> getAllSortedMembers(){
        return memberJpaRepository.findTop10MembersOrderByBestScoreSumAndTestCount();
    }

    public MemberRankingGetResponse getMemberRankingById(Long memberId) {
        List<MemberEntity> allSortedMembers = memberJpaRepository.findMembersOrderByBestScoreSumAndTestCount();
        for (int i = 0; i < allSortedMembers.size(); i++) {
            if (allSortedMembers.get(i).getId().equals(memberId)) {
                MemberRankingGetResponse memberRankingDTO = new MemberRankingGetResponse();
                memberRankingDTO.setMember(allSortedMembers.get(i));
                memberRankingDTO.setRanking(i + 1L); // 랭킹 정보 추가
                return memberRankingDTO;
            }
        }
        throw new MemberException.MemberNotFoundException("Member with id " + memberId + " not found");
    }
}
