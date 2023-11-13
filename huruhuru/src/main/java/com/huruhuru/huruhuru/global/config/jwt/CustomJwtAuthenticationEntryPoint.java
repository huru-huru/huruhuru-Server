package com.huruhuru.huruhuru.global.config.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /*
        인증되지 않은(로그인하지 않은) 사용자의 접근에 대해 401 Unauthorized 에러를 리턴하는 클래스
     */

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // 사용자가 인증되지 않은 경우에 호출되는 메서드
        setResponse(response);
    }

    private void setResponse(HttpServletResponse response) {
        // 401 Unauthorized 에러를 리턴
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
