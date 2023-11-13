package com.huruhuru.huruhuru.dto.request.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MemberLogInRequest {

    private String nickname;
    private String password;

}
