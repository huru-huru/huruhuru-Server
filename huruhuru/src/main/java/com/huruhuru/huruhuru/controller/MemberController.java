package com.huruhuru.huruhuru.controller;

import com.huruhuru.huruhuru.dto.request.member.MemberLogInRequest;
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

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    // 특정 사용자 정보를 조회하는 API

    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public Long signup(@RequestBody MemberSignInRequest request) {
        System.out.println("signup 실행됨");
        return memberService.save(request);
    }

    @PostMapping("/login")
    public MemberLogInResponse login(@RequestBody MemberSignInRequest request) {
        return memberService.login(request);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }
}
