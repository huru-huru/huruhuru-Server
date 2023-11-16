package com.huruhuru.huruhuru.global.config.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {
    /*
        JWT를 통해 인증된 사용자를 나타내는 클래스.
        사용자가 성공적으로 인정되었을 때, SecurityContextHolder에 저장되어 Spring Security가 현재 사용자를 식별할 수 있도록 함.
     */

    // 생성자
    public UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        // principal: 사용자의 식별자(사용자 ID 등)
        // credentials: 사용자의 비밀번호. JWT에서는 주로 null이 될 수 있음.
        super(principal, credentials, authorities);
    }

}
