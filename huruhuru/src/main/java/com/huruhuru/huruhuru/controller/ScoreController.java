package com.huruhuru.huruhuru.controller;


import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import com.huruhuru.huruhuru.service.ScoreService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/score")
@RequiredArgsConstructor
public class ScoreController {
}
