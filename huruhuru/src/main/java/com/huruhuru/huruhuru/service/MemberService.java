package com.huruhuru.huruhuru.service;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import com.huruhuru.huruhuru.dto.request.member.MemberSignInRequest;
import com.huruhuru.huruhuru.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 닉네임 중복 체크(중복되면 true)
     * @param nickname
     * @return
     */
    public boolean checkNicknameDuplicate(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    /**
     * 회원가입
     * @param dto
     * @return
     */
    public Long save(MemberSignInRequest dto) {
        return memberRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword()))).getId();
    }

    /**
     * 로그인
     * @param dto
     * @return
     */
    public MemberEntity login(MemberSignInRequest dto) {
        Optional<MemberEntity> optionalMember = memberRepository.findByNickname(dto.getNickname());

        // nickname이 일치하는 Member가 없으면 null 반환
        if (optionalMember.isEmpty()) {
            return null;
        }

        MemberEntity member = optionalMember.get();

        // password가 일치하지 않으면 null 반환
        if (!member.getPassword().equals(dto.getPassword())) {
            return null;
        }

        return member;
    }


    /**
     * ID로 회원 조회
     * @param id
     * @return
     */
    public MemberEntity getMember(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    /**
     * 닉네임으로 회원 조회
     * @param nickname
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));

        return member;
    }
}
