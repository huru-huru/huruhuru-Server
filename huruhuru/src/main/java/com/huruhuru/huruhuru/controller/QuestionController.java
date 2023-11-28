package com.huruhuru.huruhuru.controller;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import com.huruhuru.huruhuru.domain.entity.QuestionEntity;
import com.huruhuru.huruhuru.dto.ScoreRequest;
import com.huruhuru.huruhuru.repository.MemberJpaRepository;
import com.huruhuru.huruhuru.repository.QuestionJpaRepository;
import com.huruhuru.huruhuru.service.MemberService;
import com.huruhuru.huruhuru.service.QuestionService;
import com.huruhuru.huruhuru.global.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class QuestionController {
    private final MemberJpaRepository memberJpaRepository;

    private final QuestionService testService;

    // totalTestCount 조회
    @GetMapping
    public ResponseEntity<Long> getTotalTestCount() {
        Long totalTestCount = testService.getTotalTestCount();
        return ResponseEntity.ok(totalTestCount);
    }

    // totalTestCount 1 증가
    @PutMapping
    public ResponseEntity<Map<String, Long>> plusTotalTestCount(@RequestParam("category") Long category, @RequestParam("theme") Long theme) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userdetail= (String)principal;
        Long memberId=Long.parseLong(userdetail);

        testService.plusTotalTestCount(category, theme);

        // id로 member 찾고 testCount 증가
        Optional<MemberEntity> optionalMember = memberJpaRepository.findById(memberId);
        if (optionalMember.isPresent()) {
            MemberEntity member = optionalMember.get();
            member.increaseTestCount();
            memberJpaRepository.save(member);  // Update the member
        } else {
            throw new MemberException.MemberNotFoundException("Member with id " + memberId + " not found");
        }

        // id로 testCount 조회
        Long TestCountWithCategoryAndTheme = testService.getTestCountByCategoryAndTheme(category, theme);

        Map<String, Long> response = Map.of("testCount", TestCountWithCategoryAndTheme);
        return ResponseEntity.ok(response);

    }


    @GetMapping("/test")
    public ResponseEntity<List<QuestionEntity>> getQuestionsByCategoryAndTheme(
            @RequestParam("category") Long category,
            @RequestParam("theme") Long theme) {

        log.info("Request received for questions by category and theme");

        List<QuestionEntity> questionEntities = testService.getQuestionsByCategoryAndTheme(category, theme);

        if (questionEntities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(questionEntities, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveScore(@RequestBody ScoreRequest scoreRequest) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userdetail= (String)principal;
        Long memberId=Long.parseLong(userdetail);
        testService.saveQuestionAndScore(memberId, scoreRequest.getTheme(), scoreRequest.getScore());
        return ResponseEntity.ok("Score saved successfully.");
    }

}
