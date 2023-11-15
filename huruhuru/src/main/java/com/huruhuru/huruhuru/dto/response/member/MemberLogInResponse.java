package com.huruhuru.huruhuru.dto.response.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MemberLogInResponse {
    private String accessToken;

    public MemberLogInResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
