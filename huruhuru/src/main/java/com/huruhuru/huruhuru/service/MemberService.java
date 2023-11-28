package com.huruhuru.huruhuru.service;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import com.huruhuru.huruhuru.dto.response.member.MemberRankingGetResponse;
import com.huruhuru.huruhuru.global.CustomUserDetail;
import com.huruhuru.huruhuru.global.exception.MemberException;
import com.huruhuru.huruhuru.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.HashMap;
import java.util.List;

import com.huruhuru.huruhuru.dto.request.member.MemberAuthRequest;
import com.huruhuru.huruhuru.global.config.jwt.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = false)
public class MemberService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 회원가입
     */
    public Long save(MemberAuthRequest dto) {
        MemberEntity member = dto.toEntity(passwordEncoder.encode(dto.getPassword()));
        return memberJpaRepository.save(member).getId();
    }

    /**
     * 로그인
     */
    public Map<String, Object> login(MemberAuthRequest dto) {
        Optional<MemberEntity> optionalMember = memberJpaRepository.findByNickname(dto.getNickname());

        // nickname이 일치하는 Member가 없는 경우
        if (optionalMember.isEmpty()) {
            throw new AuthenticationException("닉네임 또는 비밀번호가 일치하지 않습니다.") {};
        }

        MemberEntity member = optionalMember.get();

        // password가 일치하지 않으면 null 반환
        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new AuthenticationException("닉네임 또는 비밀번호가 일치하지 않습니다.") {};

        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getNickname(), dto.getPassword());

        // authenticationToken 객체를 통해 Authentication 객체 생성
        // 이 과정에서 CustomUserDetailsService 에서 우리가 재정의한 loadUserByUsername 메서드 호출
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("id", member.getId());
        return body;
    }


    /**
     * ID로 회원 조회
     */
    public MemberEntity getMember(Long id) {
        return memberJpaRepository.findById(id).orElse(null);
    }

    /**
     * 닉네임으로 회원 조회
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        return memberJpaRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
    }


    /*
        랭킹 관련 로직
     */

//    public List<MemberEntity> getAllSortedMembers(){
//        return memberJpaRepository.findTop10MembersOrderByBestScoreSumAndTestCount();
//    }

    public List<MemberEntity> getAllSortedMembers(){
        return memberJpaRepository.findMembersOrderByBestScoreSumAndTestCountRank();
    }

    public MemberRankingGetResponse getMemberRankingById(Long memberId) {
        List<MemberEntity> allSortedMembers = memberJpaRepository.findMembersOrderByBestScoreSumAndTestCount();
        for (int i = 0; i < allSortedMembers.size(); i++) {
            if (allSortedMembers.get(i).getId().equals(memberId)) {
                MemberRankingGetResponse memberRankingDTO = new MemberRankingGetResponse();
                memberRankingDTO.setMember(allSortedMembers.get(i));
                memberRankingDTO.setRanking(i + 0L); // 랭킹 정보 추가
                return memberRankingDTO;
            }
        }
        throw new MemberException.MemberNotFoundException("Member with id " + memberId + " not found");
    }

}
