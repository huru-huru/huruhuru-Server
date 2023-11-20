package com.huruhuru.huruhuru.dto.request.member;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberSignInRequest {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public MemberEntity toEntity(String encodedPassword) {
        return MemberEntity.builder()
                .nickname(this.nickname)
                .password(encodedPassword)
                .testCount(0L)
                .totalBestScore(0L)
                .build();
    }

}
