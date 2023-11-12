package com.huruhuru.huruhuru.service;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import com.huruhuru.huruhuru.dto.request.member.MemberSignInRequest;
import com.huruhuru.huruhuru.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Lazy
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Long save(MemberSignInRequest dto) {
        return memberRepository.save(MemberEntity.builder()
                .nickname(dto.getNickname())
                .password(passwordEncoder.encode(dto.getPassword()))    // 패스워드는 암호화하여 저장
                .build()).getId();
    }
    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        return memberRepository.findByNickname(nickname);
    }
}
