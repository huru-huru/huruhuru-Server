package com.hruhuru.huruhuru.controller;

import com.hruhuru.huruhuru.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService testService;

    @GetMapping
    public ResponseEntity<Long> getTotalTestCount() {
        Long totalTestCount = testService.getTotalTestCount();
        return ResponseEntity.ok(totalTestCount);
    }
}
