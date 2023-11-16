package com.huruhuru.huruhuru.controller;

import com.huruhuru.huruhuru.dto.request.member.MemberSignInRequest;
import com.huruhuru.huruhuru.dto.response.member.MemberLogInResponse;
import com.huruhuru.huruhuru.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

    /*
        로그인/회원가입 관련 API
     */

    @PostMapping("signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody MemberSignInRequest request) {
        Long id = memberService.save(request);
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        return ResponseEntity.created(null).body(response);
    }

    @PostMapping("login")
    public MemberLogInResponse login(@RequestBody MemberSignInRequest request) {
        return memberService.login(request);
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }

    /*
        정보 조회 관련 API
     */

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
        response.put("Top10", memberRanking);
        response.put("MyRanking", member);
        return ResponseEntity.ok(response);
    }

    // TODO: 특정 사용자 정보를 조회하는 API

}
