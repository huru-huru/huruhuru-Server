package com.huruhuru.huruhuru.dto.response.member;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberLogInResponse {
    private String accessToken;
}
