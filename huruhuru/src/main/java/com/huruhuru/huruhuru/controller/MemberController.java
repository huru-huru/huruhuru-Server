package com.huruhuru.huruhuru.controller;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import com.huruhuru.huruhuru.dto.response.member.MemberRankingGetResponse;
import com.huruhuru.huruhuru.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 1. 모든 멤버의 점수 총합을 계산해서 멤버 리스트 가져옴
    // 2. member의 score의 best_score의 기반으로 내림차순으로 정렬
    // 3. 만약 점수가 같다면 각 멤버의 총 테스트 횟수를 비교하여 내림차순으로 정렬
    // 4. 1위부터 10위까지 순서대로 멤버의 nickname, test_count, member의 best_score 합산 반환

    // 5. 멤버 id 값으로 받은 멤버의 순위를 반환
    @GetMapping("score/{memberId}")
    public ResponseEntity<Map<String, Object>> getRanking(@PathVariable("memberId") Long memberId){
        List<MemberEntity> memberRanking = memberService.getAllSortedMembers();
        MemberRankingGetResponse member = memberService.getMemberRankingById(memberId);
        Map<String, Object> response = new HashMap<>();
        response.put("Top 10 : ", memberRanking);
        response.put("My ranking : ", member);
        return ResponseEntity.ok(response);
    }
}
