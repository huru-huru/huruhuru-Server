package com.huruhuru.huruhuru.service;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import com.huruhuru.huruhuru.dto.response.member.MemberRankingGetResponse;
import com.huruhuru.huruhuru.global.CustomUserDetail;
import com.huruhuru.huruhuru.global.exception.MemberException;
import com.huruhuru.huruhuru.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.huruhuru.huruhuru.dto.request.member.MemberSignInRequest;
import com.huruhuru.huruhuru.global.config.jwt.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 닉네임 중복 체크(중복되면 true)
     * @param nickname
     * @return
     */
    public boolean checkNicknameDuplicate(String nickname) {
        return memberJpaRepository.existsByNickname(nickname);
    }

    /**
     * 회원가입
     * @param dto
     * @return
     */
    public Long save(MemberSignInRequest dto) {
        MemberEntity member = dto.toEntity(passwordEncoder.encode(dto.getPassword()));
        return memberJpaRepository.save(member).getId();
    }

    /**
     * 로그인
     * @param dto
     * @return
     */
    public String login(MemberSignInRequest dto) {
        Optional<MemberEntity> optionalMember = memberJpaRepository.findByNickname(dto.getNickname());

        // nickname이 일치하는 Member가 없는 경우
        if (optionalMember.isEmpty()) {
            throw new AuthenticationException("닉네임이 존재하지 않습니다.") {};
        }

        MemberEntity member = optionalMember.get();

        // password가 일치하지 않으면 null 반환
        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new AuthenticationException("비밀번호가 일치하지 않습니다.") {};

        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getNickname(), dto.getPassword());

        // authenticationToken 객체를 통해 Authentication 객체 생성
        // 이 과정에서 CustomUserDetailsService 에서 우리가 재정의한 loadUserByUsername 메서드 호출
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication, 60*60*1000L); // 토큰 유효시간: 1시간
    }


    /**
     * ID로 회원 조회
     * @param id
     * @return
     */
    public MemberEntity getMember(Long id) {
        return memberJpaRepository.findById(id).orElse(null);
    }

    /**
     * 닉네임으로 회원 조회
     * @param nickname
     * @return
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
    public List<MemberEntity> getAllSortedMembers(){
        return memberJpaRepository.findTop10MembersOrderByBestScoreSumAndTestCount();
    }

    public MemberRankingGetResponse getMemberRankingById(Long memberId) {
        List<MemberEntity> allSortedMembers = memberJpaRepository.findMembersOrderByBestScoreSumAndTestCount();
        for (int i = 0; i < allSortedMembers.size(); i++) {
            if (allSortedMembers.get(i).getId().equals(memberId)) {
                MemberRankingGetResponse memberRankingDTO = new MemberRankingGetResponse();
                memberRankingDTO.setMember(allSortedMembers.get(i));
                memberRankingDTO.setRanking(i + 1L); // 랭킹 정보 추가
                return memberRankingDTO;
            }
        }
        throw new MemberException.MemberNotFoundException("Member with id " + memberId + " not found");
    }

    public Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof CustomUserDetail) {
                return ((CustomUserDetail) principal).getMemberId();
            }
        }

        return null;
    }

}
