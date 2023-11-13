package com.huruhuru.huruhuru.global.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    /*
        JWT 토큰을 생성하고 검증하는 클래스
     */

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @PostConstruct
    protected void init() {
        // JWT_SECRET 값을 Base64로 인코딩하는 메서드.
        // base64 라이브러리에서 제공하는 인코더를 사용하여 JWT_SECRET 값을 인코딩
        // encodeToString을 사용하여 byte[]를 String으로 변환
        JWT_SECRET = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication, Long expiredTokenTime) {
        final Date now = new Date();

        // Claim: JWT 토큰에 저장되는 정보
        final Claims claims = Jwts.claims()
                .setIssuedAt(now)       // 발급 시간을 지금으로 설정
                .setExpiration(new Date(now.getTime() + expiredTokenTime));     // 만료 시간 설정
        claims.put("id", authentication.getPrincipal());        // 사용자의 id를 Claim에 저장

        // JWT는 Header, Claim(payload), Signature 세 부분으로 구성되어 있음.
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() { // 서명 키를 생성하는 메서드.
        String encodedKey= Base64.getEncoder().encodeToString(JWT_SECRET.getBytes());
        return Keys.hmacShaKeyFor(encodedKey.getBytes());   // HMAC SHA 알고리즘으로 SecretKey를 생성
    }

    public JwtValidationType validateToken(String token) {
        // 토큰의 유효성 + 만료일자 확인
        try {
            final Claims claims = getBody(token);
            return JwtValidationType.VALID_JWT;
        } catch (MalformedJwtException ex) {
            return JwtValidationType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException ex) {
            return JwtValidationType.EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException ex) {
            return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException ex) {
            return JwtValidationType.EMPTY_JWT;
        }
    }

    private Claims getBody(final String token) {    // 토큰에서 Body 추출
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserFromJwt(String token) {
        Claims claims = getBody(token);
        return Long.valueOf(claims.get("id").toString());
    }

}
