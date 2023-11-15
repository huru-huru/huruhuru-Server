package com.huruhuru.huruhuru.global.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /*
        JWT를 통해 인증된 사용자를 식별하는 필터.
        JWT를 통해 인증된 사용자가 요청을 보낼 때마다 JWT의 유효성을 검사하고, 유효한 JWT인 경우 SecurityContextHolder에 사용자 정보를 저장함.
     */

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("doFilterInternal 실행됨");
        try {
            final String token = getTokenFromJwt(request);  // HTTP 요청에서 JWT 토큰 추출
            System.out.println("token: " + token);

            // JWT 토큰 유효성 검사
            if (jwtTokenProvider.validateToken(token) == JwtValidationType.VALID_JWT) {
                Long memberId = jwtTokenProvider.getUserFromJwt(token);
                System.out.println("memberId: " + memberId);

                // 사용자 인증 객체 생성
                UserAuthentication authentication = new UserAuthentication(memberId.toString(), null, null);

                // request 정보로 사용자 객체 디테일 설정
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContextHolder에 인증 객체 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Security Context에 인증 정보를 저장했습니다");
            }
        } catch(Exception exception){
            System.out.println("exception: " + exception);
//            throw new BusinessException("Invalid Token");
        }

        // 다음 필터로 넘어감
        filterChain.doFilter(request, response);
    }

    private String getTokenFromJwt(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }

        return null;
    }
}
