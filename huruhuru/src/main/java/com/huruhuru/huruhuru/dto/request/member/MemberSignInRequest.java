package com.huruhuru.huruhuru.dto.request.member;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MemberSignInRequest {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public MemberEntity toEntity(String encodedPassword) {
        return MemberEntity.builder()
                .nickname(this.nickname)
                .password(encodedPassword)
                .build();
    }

}
