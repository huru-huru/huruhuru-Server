package com.huruhuru.huruhuru.controller;

import com.huruhuru.huruhuru.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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
    public ResponseEntity<Map<String, Long>> plusTotalTestCount(@RequestParam("category") Long category, @RequestParam("theme") Long theme) {
        testService.plusTotalTestCount(category, theme);
        // id로 testCount 조회
        Long TestCountWithCategoryAndTheme = testService.getTestCountByCategoryAndTheme(category, theme);

        Map<String, Long> response = Map.of("testCount", TestCountWithCategoryAndTheme);
        return ResponseEntity.ok(response);

    }
}
