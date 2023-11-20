package com.huruhuru.huruhuru.controller;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import com.huruhuru.huruhuru.domain.entity.QuestionEntity;
import com.huruhuru.huruhuru.dto.ScoreRequest;
import com.huruhuru.huruhuru.repository.MemberJpaRepository;
import com.huruhuru.huruhuru.repository.QuestionJpaRepository;
import com.huruhuru.huruhuru.service.MemberService;
import com.huruhuru.huruhuru.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class QuestionController {
    private final MemberJpaRepository memberJpaRepository;

    private final QuestionService testService;

    private final MemberService memberService;

    // totalTestCount 조회
    @GetMapping
    public ResponseEntity<Long> getTotalTestCount() {
        Long totalTestCount = testService.getTotalTestCount();
        return ResponseEntity.ok(totalTestCount);
    }

    // totalTestCount 1 증가
    @PutMapping
    public ResponseEntity<String> plusTotalTestCount(@RequestParam("category") Long category, @RequestParam("theme") Long theme) {
        testService.plusTotalTestCount(category, theme);
        // id로 testCount 조회
        Long TestCountWithCategoryAndTheme = testService.getTestCountByCategoryAndTheme(category, theme);
        return ResponseEntity.ok("testCount 1 증가 : 현재 totalTestCount : " + TestCountWithCategoryAndTheme);
    }


    @GetMapping("/test")
    public ResponseEntity<List<QuestionEntity>> getQuestionsByCategoryAndTheme(@RequestParam("category") Long category, @RequestParam("theme") Long theme) {
        List<QuestionEntity> questionEntities = testService.getQuestionsByCategoryAndTheme(category, theme);
        return new ResponseEntity<>(questionEntities, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveScore(@RequestBody ScoreRequest scoreRequest,Authentication authentication) {
        Long memberId = memberService.getCurrentMemberId();
        log.info(String.valueOf(memberId));
        testService.saveQuestionAndScore(memberId, scoreRequest.getTheme(), scoreRequest.getScore());
        return ResponseEntity.ok("Score saved successfully.");
    }

}
