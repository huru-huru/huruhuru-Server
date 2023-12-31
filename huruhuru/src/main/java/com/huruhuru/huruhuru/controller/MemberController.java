package com.huruhuru.huruhuru.controller;

import com.huruhuru.huruhuru.dto.request.member.MemberAuthRequest;
import com.huruhuru.huruhuru.service.MemberService;
import com.huruhuru.huruhuru.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import com.huruhuru.huruhuru.dto.response.member.MemberRankingGetResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final QuestionService questionService;

    /*
        로그인/회원가입 관련 API
     */

    @PostMapping("signup") // 회원가입 + 로그인
    public ResponseEntity<Map<String, Object>> signup(@RequestBody MemberAuthRequest request) {
        Long id = memberService.save(request);
        Map<String, Object> response = memberService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody MemberAuthRequest request) {
        Map<String, Object> response = memberService.login(request);
        return ResponseEntity.ok(response);
    }


    /*
        정보 조회 관련 API
     */

    // 1. 모든 멤버의 점수 총합을 계산해서 멤버 리스트 가져옴
    // 2. member의 score의 best_score의 기반으로 내림차순으로 정렬
    // 3. 만약 점수가 같다면 각 멤버의 총 테스트 횟수를 비교하여 내림차순으로 정렬
    // 4. 1위부터 10위까지 순서대로 멤버의 nickname, test_count, member의 best_score 합산 반환
    // 5. 멤버 id 값으로 받은 멤버의 순위를 반환
    @GetMapping("score")
    public ResponseEntity<Map<String, Object>> getRanking(){
        // 토큰으로 부터 memberId 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userdetail= (String)principal;
        Long memberId=Long.parseLong(userdetail);

        List<MemberEntity> memberRanking = memberService.getAllSortedMembers();
        memberRanking.forEach(member -> {
            System.out.println("ID: " + member.getId() + ", Nickname: " + member.getNickname() + ", Score: " + member.getTotalBestScore() + ", Test Count: " + member.getTestCount());
        });
        MemberRankingGetResponse member = memberService.getMemberRankingById(memberId);
        Long memberCount = memberService.getMemberCount();
        Map<String, Object> response = new HashMap<>();
        response.put("Top10", memberRanking);
        response.put("MyRanking", member);
        response.put("test2Count", memberCount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{memberId}")
    public ResponseEntity<MemberEntity> getMember(@PathVariable("memberId") Long memberId) {
        MemberEntity member = memberService.getMember(memberId);
        return ResponseEntity.ok(member);
    }
}
