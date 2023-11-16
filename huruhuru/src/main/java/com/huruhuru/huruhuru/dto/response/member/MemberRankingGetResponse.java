package com.huruhuru.huruhuru.dto.response.member;

import com.huruhuru.huruhuru.domain.entity.MemberEntity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberRankingGetResponse {
    private MemberEntity member;
    private Long ranking;
}
