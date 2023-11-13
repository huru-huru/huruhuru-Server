package com.huruhuru.huruhuru.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BCryptConfig {

    // 비밀번호를 암호화 해주는 BCryptPasswordEncoder
    @Bean   // 비밀번호 암호화, 비밀번호 체크할 때 사용
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
