package com.huruhuru.huruhuru.controller;


import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import com.huruhuru.huruhuru.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/score")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    // 1. 모든 멤버의 점수 총합을 가져옴
    // 2. 점수 총합을 내림차순으로 정렬
    // 3. 만약 점수가 같다면 각 멤버의 총 테스트 횟수를 비교하여 높은 순으로 정렬
    // 4. 1위부터 10위까지 순서대로 멤버 entity 반환
    // 5. 멤버 id 값으로 받은 멤버의 순위를 반환
    @GetMapping
    public ResponseEntity<MemberEntity> getRanking(){
        return null;
    }
}
