package com.huruhuru.huruhuru.controller;

import com.huruhuru.huruhuru.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService testService;

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
}
