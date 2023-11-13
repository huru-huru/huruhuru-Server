package com.huruhuru.huruhuru.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    @Bean   // Spring Security 기능(인증, 인가) 비활성화
//    public WebSecurityCustomizer configure() {
//        return (web) -> web.ignoring()
//                .requestMatchers("/")
//                .requestMatchers("/static/**");
//    }

    @Bean   // 특정 HTTP 요청에 대한 웹 기반 보안 구성. (인증인가, 로그인, 로그아웃 설정)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize   // 모든 HTTP 요청에 대한 보안 규칙 정의
//                        .requestMatchers("/login", "/signup").permitAll()
                        .anyRequest().permitAll()   // 모든 요청에 대해 인증 없이 접근 가능
                )
                .formLogin(login -> login
                        .loginPage("/login")    // 로그인 페이지의 경로를 '/login'으로 설정
                        .defaultSuccessUrl("/") // 로그인 성공 시 이동할 경로
                )
                .logout(logout ->
                        logout.logoutSuccessUrl("/").invalidateHttpSession(true)
                )
                .csrf(AbstractHttpConfigurer::disable)  // 실제 운영 환경에서는 활성화하는 걸 권장
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin));
                // headers(): X-Frame-Options 헤더를 설정하여, 같은 출처에서만 해당 페이지를 포함하는 프레임에 표시되도록

        // 모든 요청에 대해 인증을 필요로하는 코드
//        http.authorizeHttpRequests((authz) -> authz.anyRequest().authenticated())
//                .httpBasic(withDefaults());
        return http.build();
    }

}
