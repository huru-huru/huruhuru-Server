package com.huruhuru.huruhuru.dto.request.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberSignInRequest {

    private String nickname;
    private String password;
}
